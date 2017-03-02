/**
 * Created on 1/12/16 11:01 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.account.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.account.User;
import com.decipherzone.ies.persistence.entities.nullref.account.NullUser;
import com.decipherzone.ies.persistence.enums.RoleType;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.account.UserRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;
    private ArangoDatabase arangoDatabase;

    @Inject
    public UserRepositoryImpl(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase) {
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);

        this.arangoDatabaseAsync = arangoDatabaseAsync;

        this.arangoDatabase = arangoDatabase;
    }

    @Override
    public User getUserByUsername(String username) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for user in ");
        stringBuilder.append(EntityColumnNames.USERS);
        stringBuilder.append(" filter user.");
        stringBuilder.append(EntityColumnNames.USERNAME);
        stringBuilder.append("==@username return user");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("username", username);


        User user = null;
        try {
            ArangoCursor<User> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, User.class);
            if (cursor.hasNext()) {
                user = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting user by username", e);
        }


        return user == null ? new NullUser() : user;
    }

    @Override
    public User getUserByKey(String key) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for user in ");
        stringBuilder.append(EntityColumnNames.USERS);
        stringBuilder.append(" filter user.");
        stringBuilder.append(EntityColumnNames._KEY);
        stringBuilder.append("==@key return user");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("key", key);

        ArangoCursor<User> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, User.class);

        User user = null;
        try {
            if (cursor.hasNext()) {
                user = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting user by username", e);
        }

        return user == null ? new NullUser() : user;
    }

    @Override
    public User getUserByEmail(String email) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for user in ");
        stringBuilder.append(EntityColumnNames.USERS);
        stringBuilder.append(" filter user.");
        stringBuilder.append(EntityColumnNames.EMAIL);
        stringBuilder.append("==@email return user");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("email", email);

        ArangoCursor<User> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, User.class);

        User user = null;
        try {
            if (cursor.hasNext()) {
                user = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting user by email ", e);
        }
        return user == null ? new NullUser() : user;
    }

    @Override
    public User getUserByEmailIfEnabled(String email) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for user in ");
        stringBuilder.append(EntityColumnNames.USERS);
        stringBuilder.append(" filter user.");
        stringBuilder.append(EntityColumnNames.EMAIL);
        stringBuilder.append("==@email and user.");
        stringBuilder.append(EntityColumnNames.IS_ENABLED);
        stringBuilder.append("==true and  return user");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("email", email);

        ArangoCursor<User> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, User.class);

        User user = null;
        try {
            if (cursor.hasNext()) {
                user = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting user by email ", e);
        }
        return user == null ? new NullUser() : user;
    }

    @Override
    public User saveUser(User user) throws DatabaseOperationException {
        return this.basicRepository.save(User.class, user);
    }

    @Override
    public User updateUser(User user) throws DatabaseOperationException {
        return this.basicRepository.update(User.class, user);
    }

    @Override
    public List<User> getAllExceptAdmin() {

        String query = "for user in " +
                EntityColumnNames.USERS +
                " filter user." +
                EntityColumnNames.ROLE +
                "!=@superAdmin and user." +
                EntityColumnNames.ROLE +
                "!=@admin  and user." +
                EntityColumnNames.ROLE +
                "!=@user return user";

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("admin", RoleType.ROLE_ADMIN);
        bindVars.put("superAdmin", RoleType.ROLE_SUPER_ADMIN);
        bindVars.put("user", RoleType.ROLE_USER);

        List<User> userList = new ArrayList<>();
        try {
            userList = arangoDatabase.query(query, bindVars, null, User.class).asListRemaining();

        } catch (Exception e) {
            ApplicationLogger.error("Error while getting user by email ", e);
        }

        return userList;

    }

    @Override
    public Boolean deleteUser(String key) {
        boolean status;
        try {
            arangoDatabaseAsync.collection(EntityColumnNames.USERS).deleteDocument(key);
            status = true;
        } catch (Exception e) {
            status = false;
            ApplicationLogger.error("Error while deletion of section by key : " + key, e);
        }
        return status;
    }
}
