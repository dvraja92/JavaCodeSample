/**
 * Created on 29/11/16 7:56 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository;

import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.repository.impl.BasicRepositoryImpl;

public interface BasicRepository {
    static BasicRepository getInstance(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase){
        return new BasicRepositoryImpl(arangoDatabaseAsync, arangoDatabase);
    }
    <T> T save(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException;
    <T> T update(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException;
    void delete(AbstractEntity ae) throws DatabaseOperationException;
    <T> T saveOrUpdate(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException;
}
