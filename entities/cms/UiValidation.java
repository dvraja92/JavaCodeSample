package com.decipherzone.ies.persistence.entities.cms;

import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.enums.UiValidationType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created on 22/12/16 3:12 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class UiValidation {

    @SerializedName(EntityColumnNames.VALIDATION_TYPE)
    private UiValidationType uiValidationType;
    @SerializedName(EntityColumnNames.IS_TO_BE_APPLIED)
    private Boolean isToBeApplied;
    @SerializedName(EntityColumnNames.CODE)
    private String code;

    public UiValidationType getUiValidationType() {
        return uiValidationType;
    }

    public void setUiValidationType(UiValidationType uiValidationType) {
        this.uiValidationType = uiValidationType;
    }

    public Boolean getToBeApplied() {
        return isToBeApplied;
    }

    public void setToBeApplied(Boolean toBeApplied) {
        isToBeApplied = toBeApplied;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ValidationFieldNames {

    }

}
