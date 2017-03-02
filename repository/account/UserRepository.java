/**
 * Created on 1/12/16 11:00 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.account;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.account.User;

import java.util.List;

public interface UserRepository {
    User getUserByUsername(String username);

    User getUserByKey(String key);

    User getUserByEmail(String email);

    User getUserByEmailIfEnabled(String email);

    User saveUser(User user) throws DatabaseOperationException;

    User updateUser(User user) throws DatabaseOperationException;

    List<User> getAllExceptAdmin();

    Boolean deleteUser(String key);
}
