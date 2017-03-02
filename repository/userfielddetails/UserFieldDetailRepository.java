package com.decipherzone.ies.persistence.repository.userfielddetails;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.userfielddetail.UserFieldDetail;

/**
 * Created on 4/1/17 1:25 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public interface UserFieldDetailRepository {

    UserFieldDetail save(UserFieldDetail userFieldDetail) throws DatabaseOperationException;

    void delete(UserFieldDetail userFieldDetail) throws DatabaseOperationException;

    UserFieldDetail update(UserFieldDetail userFieldDetail) throws DatabaseOperationException;

    UserFieldDetail getUserFieldDetailByKey(String key);

//    UserFieldDetail getUserFieldDetailByFieldKey(String key);

    UserFieldDetail getUserFieldDetailByUserKey(String userKey);

//    UserFieldDetail getUserFieldDetailByFieldKeyAndUserKey(String key, String userKey);
}
