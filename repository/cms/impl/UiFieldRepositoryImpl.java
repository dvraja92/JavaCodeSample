package com.decipherzone.ies.persistence.repository.cms.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.cms.UiField;
import com.decipherzone.ies.persistence.entities.nullref.cms.NullUiField;
import com.decipherzone.ies.persistence.enums.UiFieldType;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.cms.UiFieldRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 23/12/16 11:05 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Repository
public class UiFieldRepositoryImpl implements UiFieldRepository {

    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;
    private ArangoDatabase arangoDatabase;

    @Inject
    UiFieldRepositoryImpl(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase) {
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.arangoDatabase = arangoDatabase;
    }

    @Override
    public UiField saveUiField(UiField uiField) throws DatabaseOperationException {
        return basicRepository.save(UiField.class, uiField);
    }

    @Override
    public UiField updateUiField(UiField uiField) throws DatabaseOperationException {
        return basicRepository.update(UiField.class, uiField);
    }

    @Override
    public boolean deleteUiField(String key) {
        boolean status;
        try {
            arangoDatabaseAsync.collection(EntityColumnNames.UI_FIELD).deleteDocument(key);
            status = true;
        } catch (Exception e) {
            status = false;
            ApplicationLogger.error("Error while deletion of section by key : " + key, e);
        }
        return status;
    }

    @Override
    public UiField getUiFieldById(String id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiField in ");
        stringBuilder.append(EntityColumnNames.UI_FIELD);
        stringBuilder.append(" filter uiField.");
        stringBuilder.append(EntityColumnNames._ID);
        stringBuilder.append("==@id return uiField");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("id", id);

        ArangoCursor<UiField> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UiField.class);

        UiField uiField = null;
        try {
            if (cursor.hasNext()) {
                uiField = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while UiField by id : " + id, e);
        }

        return uiField == null ? new NullUiField() : uiField;
    }

    @Override
    public UiField getUiFieldByLabel(String fieldLabel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiField in ");
        stringBuilder.append(EntityColumnNames.UI_FIELD);
        stringBuilder.append(" filter uiField.");
        stringBuilder.append(EntityColumnNames.UI_FIELD_LABEL);
        stringBuilder.append("==@fieldLabel return uiField");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("fieldLabel", fieldLabel);

        ArangoCursor<UiField> cursor = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UiField.class);

        UiField uiField = null;
        try {
            if (cursor.hasNext()) {
                uiField = cursor.next();
            }
        } catch (Exception e) {
            ApplicationLogger.error("Error while UiField by fieldLabel : " + fieldLabel, e);
        }

        return uiField == null ? new NullUiField() : uiField;
    }

    @Override
    public List<UiField> getAllUiField() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiField in ");
        stringBuilder.append(EntityColumnNames.UI_FIELD);
        stringBuilder.append(" return uiField");

        List<UiField> uiFieldList = new ArrayList<>();

        try {
            uiFieldList = arangoDatabase.query(stringBuilder.toString(), null, null, UiField.class).asListRemaining();
        } catch (ArangoDBException e) {
            ApplicationLogger.error("Error while all UiField", e);
        }

        return uiFieldList;
    }

    @Override
    public List<UiField> getAllUiFieldByType(UiFieldType uiFieldType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("for uiField in ");
        stringBuilder.append(EntityColumnNames.UI_FIELD);
        stringBuilder.append(" filter uiField.");
        stringBuilder.append(EntityColumnNames.UI_FIELD_TYPE);
        stringBuilder.append("==@uiFieldType return uiField");

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("uiFieldType", uiFieldType.getUiFieldTypeStr());

        List<UiField> uiFieldList = new ArrayList<>();

        try {
            uiFieldList = arangoDatabase.query(stringBuilder.toString(), bindVars, null, UiField.class).asListRemaining();
        } catch (ArangoDBException e) {
            ApplicationLogger.error("Error while all UiField", e);
        }

        return uiFieldList;
    }
}
