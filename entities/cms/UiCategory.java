package com.decipherzone.ies.persistence.entities.cms;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.enums.UiCategorizationIterationLevel;
import com.decipherzone.ies.persistence.enums.UiCategorizationType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

/**
 * Created on 17/12/16 1:59 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@DBCollection(name = EntityColumnNames.UI_CATEGORY)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class UiCategory implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String uiCategoryId;
    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String uiCategoryKey;
    @SerializedName(EntityColumnNames.UI_CATEGORY_NAME)
    private String uiCategoryName;
    @SerializedName(EntityColumnNames.UI_CATEGORY_ICON_CLASS)
    private String uiCategoryIconClass;
    @SerializedName(EntityColumnNames.UI_CATEGORY_ICON_IMAGE_PATH)
    private String uiCategoryIconImagePath;
    @SerializedName(EntityColumnNames.UI_CATEGORY_DESCRIPTION)
    private String uiCategoryDescription;
    @SerializedName(EntityColumnNames.UI_CATEGORY_BANNER_FILE_PATH)
    private String uiCategoryBannerFilePath;
    @SerializedName(EntityColumnNames.UI_CATEGORY_ITERATION_LEVEL)
    private UiCategorizationIterationLevel uiCategorizationIterationLevel;
    @SerializedName(EntityColumnNames.UI_CATEGORY_TYPE)
    private UiCategorizationType uiCategorizationType;

    @Override
    public String getId() {
        return uiCategoryId;
    }

    @Override
    public void setId(String id) {
        this.uiCategoryId = id;
    }

    @Override
    public String getKey() {
        return uiCategoryKey;
    }

    @Override
    public void setKey(String key) {
        this.uiCategoryKey = key;
    }

    public String getUiCategoryName() {
        return uiCategoryName;
    }

    public void setUiCategoryName(String uiCategoryName) {
        this.uiCategoryName = uiCategoryName;
        StringBuilder key = new StringBuilder();
        key.append(this.uiCategoryName.replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
        key.append("_");
        key.append(EntityColumnNames.UI_CATEGORY.toLowerCase());
        key.append(EntityColumnNames.UI_CATEGORY_TYPE.toLowerCase());
//        setKey(key.toString());
    }

    public String getUiCategoryIconClass() {
        return uiCategoryIconClass;
    }

    public void setUiCategoryIconClass(String uiCategoryIconClass) {
        this.uiCategoryIconClass = uiCategoryIconClass;
    }

    public String getUiCategoryIconImagePath() {
        return uiCategoryIconImagePath;
    }

    public void setUiCategoryIconImagePath(String uiCategoryIconImagePath) {
        this.uiCategoryIconImagePath = uiCategoryIconImagePath;
    }

    public String getUiCategoryDescription() {
        return uiCategoryDescription;
    }

    public void setUiCategoryDescription(String uiCategoryDescription) {
        this.uiCategoryDescription = uiCategoryDescription;
    }

    public String getUiCategoryBannerFilePath() {
        return uiCategoryBannerFilePath;
    }

    public void setUiCategoryBannerFilePath(String uiCategoryBannerFilePath) {
        this.uiCategoryBannerFilePath = uiCategoryBannerFilePath;
    }

    public UiCategorizationIterationLevel getUiCategorizationIterationLevel() {
        return uiCategorizationIterationLevel;
    }

    public void setUiCategorizationIterationLevel(UiCategorizationIterationLevel uiCategorizationIterationLevel) {
        this.uiCategorizationIterationLevel = uiCategorizationIterationLevel;
    }

    public UiCategorizationType getUiCategorizationType() {
        return uiCategorizationType;
    }

    public void setUiCategorizationType(UiCategorizationType uiCategorizationType) {
        this.uiCategorizationType = uiCategorizationType;
    }

    public static class UiCategoryCollectionFieldNames implements AbstractColumnNamesEntity {

        @Override
        public String getCollectionName() {
            return EntityColumnNames.UI_CATEGORY;
        }

    }

    @Override
    @JsonIgnore
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new UiCategoryCollectionFieldNames();
    }

}
