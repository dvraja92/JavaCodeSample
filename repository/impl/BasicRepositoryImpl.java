/**
 * Created on 29/11/16 7:59 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.impl;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCollectionAsync;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.model.DocumentUpdateOptions;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

public class BasicRepositoryImpl implements BasicRepository {

    private ArangoDatabaseAsync arangoDatabaseAsync;
    private ArangoDatabase arangoDatabase;

    public BasicRepositoryImpl(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase) {
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.arangoDatabase = arangoDatabase;
    }

    @Override
    @Transactional
    public <T> T save(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException {
        try {
            ArangoCollection arangoCollection = arangoDatabase.collection(ae.getColumnsDefEntity().getCollectionName());
            DocumentCreateEntity<AbstractEntity> abstractEntityDocumentCreateEntity = arangoCollection.insertDocument(ae);
            ae.setId(abstractEntityDocumentCreateEntity.getId());
            ae.setKey(abstractEntityDocumentCreateEntity.getKey());


        } catch (Exception e) {
            ApplicationLogger.error("Error while saving collection : " + ae.getColumnsDefEntity().getCollectionName(), e);
        }

        return clazz.cast(ae);
    }

    @Override
    @Transactional
    public <T> T update(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException {

        try {
            DocumentUpdateOptions documentUpdateOptions = new DocumentUpdateOptions();
            documentUpdateOptions.returnNew(true);
            documentUpdateOptions.mergeObjects(true);
//            ArangoCollectionAsync arangoCollectionAsync = arangoDatabaseAsync.collection(ae.getColumnsDefEntity().getCollectionName());
//            arangoCollectionAsync.updateDocument(ae.getKey(), ae, documentUpdateOptions);

            ArangoCollection collection = arangoDatabase.collection(ae.getColumnsDefEntity().getCollectionName());
            collection.updateDocument(ae.getKey(), ae, documentUpdateOptions);


        } catch (Exception e) {
            ApplicationLogger.error("Error while updating collection : " + ae.getColumnsDefEntity().getCollectionName(), e);
        }

        return clazz.cast(ae);
    }

    @Override
    @Transactional
    public void delete(AbstractEntity ae) throws DatabaseOperationException {
        try {
            ArangoCollectionAsync arangoCollectionAsync = arangoDatabaseAsync.collection(ae.getColumnsDefEntity().getCollectionName());
            arangoCollectionAsync.deleteDocument(ae.getKey());
        } catch (Exception e) {
            ApplicationLogger.error("Error while saving collection : " + ae.getColumnsDefEntity().getCollectionName(), e);
        }
    }

    @Override
    @Transactional
    public <T> T saveOrUpdate(Class<T> clazz, AbstractEntity ae) throws DatabaseOperationException {
        try {
            ArangoCollectionAsync arangoCollectionAsync = arangoDatabaseAsync.collection(ae.getColumnsDefEntity().getCollectionName());
            CompletableFuture<? extends AbstractEntity> document = arangoCollectionAsync.getDocument(ae.getKey(), ae.getAbstractEntity().getClass());
            if (document != null){
                return update(clazz, ae);
            } else {
                return save(clazz, ae);
            }

        } catch (Exception e) {
            ApplicationLogger.error("Error while saving collection : " + ae.getColumnsDefEntity().getCollectionName(), e);
        }
        return clazz.cast(ae);
    }
}
