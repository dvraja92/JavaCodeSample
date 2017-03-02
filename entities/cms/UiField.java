package com.decipherzone.ies.persistence.entities.cms;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.enums.UiFieldType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 22/12/16 2:35 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@DBCollection(name = EntityColumnNames.UI_FIELD)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class UiField implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String uiFieldId;
    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String uiFieldKey;
    @SerializedName(EntityColumnNames.UI_FIELD_LABEL)
    private String uiFieldLabel;
    @SerializedName(EntityColumnNames.UI_FIELD_DEFAULT_VALUE)
    private String uiFieldDefaultValue;
    @SerializedName(EntityColumnNames.UI_FIELD_TYPE)
    private UiFieldType uiFieldType;
    @SerializedName(EntityColumnNames.UI_FIELD_VALIDATION_LIST)
    private List<UiValidation> uiValidationList;
    @SerializedName(EntityColumnNames.UI_FIELD_CSS_CLASS)
    private String cssClass;
    @SerializedName(EntityColumnNames.UI_FIELD_CSS_RAW)
    private String cssRaw;
    @SerializedName(EntityColumnNames.UI_FIELD_NORMAL_OR_MASTER)
    private String uiFieldNormalOrMaster;

    @Override
    public String getId() {
        return uiFieldId;
    }

    @Override
    public void setId(String id) {
        this.uiFieldId = id;
    }

    @Override
    public String getKey() {
        return uiFieldKey;
    }

    @Override
    public void setKey(String key) {
        this.uiFieldKey = key;
    }

    public String getUiFieldLabel() {
        return uiFieldLabel;
    }

    public void setUiFieldLabel(String uiFieldLabel) {
        this.uiFieldLabel = uiFieldLabel;
        StringBuilder key = new StringBuilder();
        key.append(this.uiFieldLabel.replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
        key.append("_");
        key.append(EntityColumnNames.UI_FIELD.toLowerCase());
//        setKey(key.toString());
    }

    public String getUiFieldDefaultValue() {
        return uiFieldDefaultValue;
    }

    public void setUiFieldDefaultValue(String uiFieldDefaultValue) {
        this.uiFieldDefaultValue = uiFieldDefaultValue;
    }

    public UiFieldType getUiFieldType() {
        return uiFieldType;
    }

    public void setUiFieldType(UiFieldType uiFieldType) {
        this.uiFieldType = uiFieldType;
    }

    public List<UiValidation> getUiValidationList() {
        return uiValidationList;
    }

    public void setUiValidationList(List<UiValidation> uiValidationList) {
        this.uiValidationList = uiValidationList;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssRaw() {
        return cssRaw;
    }

    public void setCssRaw(String cssRaw) {
        this.cssRaw = cssRaw;
    }

    public String getUiValidationListStr(){
        StringBuilder str = new StringBuilder();

            List<UiValidation> uiValidationList = getUiValidationList();
            if (uiValidationList != null) {
                for (UiValidation uiValidation : uiValidationList) {
                    str.append(" " + uiValidation.getUiValidationType().toString());
                }
            }

        return str.toString();
    }


    public String getUiFieldNormalOrMaster() {
        return uiFieldNormalOrMaster;
    }

    public void setUiFieldNormalOrMaster(String uiFieldNormalOrMaster) {
        this.uiFieldNormalOrMaster = uiFieldNormalOrMaster;
    }

    public static class UiFieldCollectionFieldNames implements AbstractColumnNamesEntity{


        @Override
        public String getCollectionName() {
            return EntityColumnNames.UI_FIELD;
        }
    }


    @Override
    @JsonIgnore
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new UiFieldCollectionFieldNames();
    }
}
