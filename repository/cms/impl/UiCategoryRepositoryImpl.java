package com.decipherzone.ies.persistence.repository.cms.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.cms.UiCategory;
import com.decipherzone.ies.persistence.entities.nullref.cms.NullUiCategory;
import com.decipherzone.ies.persistence.enums.UiCategorizationType;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.cms.UiCategoryRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 17/12/16 2:06 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Repository
public class UiCategoryRepositoryImpl implements UiCategoryRepository {

    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;
    private ArangoDatabase arangoDatabase;

    @Inject
    public UiCategoryRepositoryImpl(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase) {
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.arangoDatabase = arangoDatabase;
    }

    @Override
    public UiCategory saveUiCategory(UiCategory uiCategory) throws DatabaseOperationException {
        return basicRepository.save(UiCategory.class, uiCategory);
    }

    @Override
    public UiCategory updateUiCategory(UiCategory uiCategory) throws DatabaseOperationException {
        return basicRepository.update(UiCategory.class, uiCategory);
    }

    @Override
    public boolean deleteUiCategory(String key) {
        boolean status;
        try {
            arangoDatabaseAsync.collection(EntityColumnNames.UI_CATEGORY).deleteDocument(key);
            status = true;
        } catch (Exception e) {
            status = false;
            ApplicationLogger.error("Error while deletion of section by key : " + key, e);
        }
        return status;
    }

    @Override
    public UiCategory getUiCategoryById(String id) {
        String query = " for doc in " + EntityColumnNames.UI_CATEGORY + " filter doc." + EntityColumnNames._ID + " == @id return doc";

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("id", id);

        ArangoCursor<UiCategory> cursor = arangoDatabase.query(query, bindVars, null, UiCategory.class);

        UiCategory uiCategory = null;
        try {
            if (cursor.hasNext()) {
                uiCategory = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.debug("Error while uiCategory by id : " + id, e);
            ApplicationLogger.error("Error while uiCategory by id : " + id);
        }

        return uiCategory == null ? new NullUiCategory() : uiCategory;
    }

    @Override
    public UiCategory getUiCategoryByName(String sectionName, UiCategorizationType uiCategorizationType){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiCategory in ");
        stringBuilder.append(EntityColumnNames.UI_CATEGORY);
        stringBuilder.append(" filter uiCategory.");
        stringBuilder.append(EntityColumnNames.UI_CATEGORY_NAME);
        stringBuilder.append("==@uiCategoryName and uiCategory.");
        stringBuilder.append(EntityColumnNames.UI_CATEGORY_TYPE);
        stringBuilder.append("==@uiCategoryType return uiCategory");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("uiCategoryName", sectionName);
        bindVars.put("uiCategoryType", uiCategorizationType.getCategorizationTypeStr());

        ArangoCursor<UiCategory> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UiCategory.class);

        UiCategory uiCategory = null;
        try {
            if (cursor.hasNext()) {
                uiCategory = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while getting uiCategory by name : " + sectionName, e);
        }

        return uiCategory == null ? new NullUiCategory() : uiCategory;
    }

    @Override
    public List<UiCategory> getAllUiCategory(UiCategorizationType uiCategorizationType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiCategory in ");
        stringBuilder.append(EntityColumnNames.UI_CATEGORY);
        stringBuilder.append(" filter uiCategory.");
        stringBuilder.append(EntityColumnNames.UI_CATEGORY_TYPE);
        stringBuilder.append("==@uiCategoryType return uiCategory");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("uiCategoryType", uiCategorizationType.getCategorizationTypeStr());

        List<UiCategory> uiCategoryList = new ArrayList<>();
        try {
            uiCategoryList = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UiCategory.class).asListRemaining();
        } catch (ArangoDBException e) {
            ApplicationLogger.error("Error while getting uiCategory list", e);
        }

        return uiCategoryList;

    }
}
