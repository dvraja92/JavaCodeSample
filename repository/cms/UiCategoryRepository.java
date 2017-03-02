package com.decipherzone.ies.persistence.repository.cms;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.cms.UiCategory;
import com.decipherzone.ies.persistence.enums.UiCategorizationType;

import java.util.List;

/**
 * Created on 17/12/16 2:06 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public interface UiCategoryRepository {
    UiCategory saveUiCategory(UiCategory uiCategory) throws DatabaseOperationException;
    UiCategory updateUiCategory(UiCategory uiCategory) throws DatabaseOperationException;
    boolean deleteUiCategory(String key);
    UiCategory getUiCategoryById(String id);
    UiCategory getUiCategoryByName(String sectionName, UiCategorizationType uiCategorizationType);
    List<UiCategory> getAllUiCategory(UiCategorizationType uiCategorizationType);
}
