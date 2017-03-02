package com.decipherzone.ies.persistence.repository.userfielddetails.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.nullref.userFieldDetail.NullUserFieldDetail;
import com.decipherzone.ies.persistence.entities.userfielddetail.UserFieldDetail;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.userfielddetails.UserFieldDetailRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 4/1/17 1:26 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Repository
public class UserFieldDetailRepositoryImpl implements UserFieldDetailRepository {

    private ArangoDatabase arangoDatabase;
    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;

    @Inject
    public UserFieldDetailRepositoryImpl(ArangoDatabase arangoDatabase, ArangoDatabaseAsync arangoDatabaseAsync) {
        this.arangoDatabase = arangoDatabase;
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
    }

    @Override
    public UserFieldDetail save(UserFieldDetail userFieldDetail) throws DatabaseOperationException {
        return basicRepository.save(UserFieldDetail.class, userFieldDetail);
    }

    @Override
    public void delete(UserFieldDetail userFieldDetail) throws DatabaseOperationException {
        basicRepository.delete(userFieldDetail);
    }

    @Override
    public UserFieldDetail update(UserFieldDetail userFieldDetail) throws DatabaseOperationException {
        return basicRepository.update(UserFieldDetail.class, userFieldDetail);
    }

    @Override
    public UserFieldDetail getUserFieldDetailByKey(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for userfielddetail in ");
        stringBuilder.append(EntityColumnNames.USER_FIELD_DETAIL);
        stringBuilder.append(" filter userfielddetail.");
        stringBuilder.append(EntityColumnNames._KEY);
        stringBuilder.append("==@key return userfielddetail");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("key", key);

        ArangoCursor<UserFieldDetail> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UserFieldDetail.class);

        UserFieldDetail userFieldDetail = null;
        try {
            if (cursor.hasNext()) {
                userFieldDetail = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting userfielddetail by key : " + key, e);
        }

        return userFieldDetail == null ? new NullUserFieldDetail() : userFieldDetail;
    }

//    @Override
//    public UserFieldDetail getUserFieldDetailByFieldKey(String fieldId) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("for userfielddetail in ");
//        stringBuilder.append(EntityColumnNames.USER_FIELD_DETAIL);
//        stringBuilder.append(" filter userfielddetail.");
////        stringBuilder.append(EntityColumnNames.FIELD_KEY);
//        stringBuilder.append("==@fieldKey return userfielddetail");
//
//        Map<String, Object> bindVars = new HashMap<>();
//        bindVars.put("fieldKey", fieldId);
//
//        ArangoCursor<UserFieldDetail> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UserFieldDetail.class);
//
//        UserFieldDetail userFieldDetail = null;
//        try {
//            if (cursor.hasNext()) {
//                userFieldDetail = cursor.next();
//            }
//        } catch (Exception e) {
//            ApplicationLogger.error("Error while getting userfielddetail by fieldId : " + fieldId, e);
//        }
//
//        return userFieldDetail == null ? new NullUserFieldDetail() : userFieldDetail;
//    }
//
//    @Override
//    public UserFieldDetail getUserFieldDetailByFieldKeyAndUserKey(String key, String userKey) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("for userfielddetail in ");
//        stringBuilder.append(EntityColumnNames.USER_FIELD_DETAIL);
//        stringBuilder.append(" filter userfielddetail.");
//        stringBuilder.append(EntityColumnNames.FIELD_KEY);
//        stringBuilder.append("==@fieldKey && userfielddetail.");
//        stringBuilder.append(EntityColumnNames.USER_KEY);
//        stringBuilder.append("==@userKey return userfielddetail");
//
//        Map<String, Object> bindVars = new HashMap<>();
//        bindVars.put("fieldKey", key);
//        bindVars.put("userKey", userKey.toString());
//
//
//        UserFieldDetail userFieldDetail = null;
//        ArangoCursor<UserFieldDetail> cursor = null;
//        try {
//            cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UserFieldDetail.class);
//            if (cursor != null && cursor.hasNext()) {
//                userFieldDetail = cursor.next();
//            }
//        } catch (Exception e) {
//            ApplicationLogger.debug("Error while getting userfielddetail by fieldId and user key", e);
//            ApplicationLogger.error("Error while getting userfielddetail by fieldId and user key");
//        }
//
//        return userFieldDetail == null ? new NullUserFieldDetail() : userFieldDetail;
//    }


    @Override
    public UserFieldDetail getUserFieldDetailByUserKey(String userKey) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for userfielddetail in ");
        stringBuilder.append(EntityColumnNames.USER_FIELD_DETAIL);
        stringBuilder.append(" filter userfielddetail.");
        stringBuilder.append(EntityColumnNames.USER_KEY);
        stringBuilder.append("==@userKey return userfielddetail");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("userKey", userKey.toString());


        UserFieldDetail userFieldDetail = null;
        ArangoCursor<UserFieldDetail> cursor = null;
        try {
            cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UserFieldDetail.class);
            if (cursor != null && cursor.hasNext()) {
                userFieldDetail = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.debug("Error while getting userfielddetail by user key", e);
            ApplicationLogger.error("Error while getting userfielddetail by user key");
        }

        return userFieldDetail == null ? new NullUserFieldDetail() : userFieldDetail;
    }
}
