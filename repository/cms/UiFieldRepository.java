package com.decipherzone.ies.persistence.repository.cms;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.cms.UiField;
import com.decipherzone.ies.persistence.enums.UiFieldType;

import java.util.List;

/**
 * Created on 23/12/16 11:02 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
public interface UiFieldRepository {

    UiField saveUiField(UiField uiField) throws DatabaseOperationException;
    UiField updateUiField(UiField uiField) throws DatabaseOperationException;
    boolean deleteUiField(String key);
    UiField getUiFieldById(String key);
    UiField getUiFieldByLabel(String fieldLabel);
    List<UiField> getAllUiField();
    List<UiField> getAllUiFieldByType(UiFieldType uiFieldType);


}
