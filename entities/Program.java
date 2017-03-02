/**
 * Created on 23/12/16 6:21 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.Expose;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.Application;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.annotation.DBVertexCollection;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@DBCollection(name = EntityColumnNames.PROGRAMS)
@DBVertexCollection(name = EntityColumnNames.GRAPH_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class Program implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String programId;
    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String programKey;
    @SerializedName(EntityColumnNames.PROGRAMNAME)
    private String programName;
    @SerializedName(EntityColumnNames.SHORTNAME)
    private String shortName;
    @Expose(serialize = false)
    @SerializedName(EntityColumnNames.AGENCYPROGRAM)
    private Agency agency;
    @SerializedName(EntityColumnNames.AGENCY)
    private String agencyId;
    @SerializedName(EntityColumnNames.INFO)
    private String infoText;
    @SerializedName(EntityColumnNames.MEMBERCOUNT_FIELDID)
    private String memberCountFieldId;
    @SerializedName(EntityColumnNames.PROGRAM_PREFERENCE_TYPE)
    private String programPreferenceType;
    @SerializedName(EntityColumnNames.OLD_PROGRAM_KEY)
    private String oldProgramKey;
    @SerializedName(EntityColumnNames.CREATED_DATE)
    private String createdDate;


    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public String getId() {
        return this.programId;
    }

    @Override
    public void setId(String id) {
        this.programId = id;
    }

    @Override
    public String getKey() {
        return this.programKey;
    }

    @Override
    public void setKey(String key) {
        this.programKey = key;
    }

    @Override
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new ProgramCollectionFieldName();
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getMemberCountFieldId() {
        return memberCountFieldId;
    }

    public void setMemberCountFieldId(String memberCountFieldId) {
        this.memberCountFieldId = memberCountFieldId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramKey() {
        return programKey;
    }

    public void setProgramKey(String programKey) {
        this.programKey = programKey;
    }

    public String getProgramPreferenceType() {
        return programPreferenceType;
    }

    public void setProgramPreferenceType(String programPreferenceType) {
        this.programPreferenceType = programPreferenceType;
    }

    public String getOldProgramKey() {
        return oldProgramKey;
    }

    public void setOldProgramKey(String oldProgramKey) {
        this.oldProgramKey = oldProgramKey;
    }

    public Date getCreatedDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Application.DATE_FORMAT);
        if (createdDate != null){
            try {
                return dateFormatter.parse(createdDate);
            } catch (Exception e) {
                ApplicationLogger.debug(e.getMessage(), e);
                ApplicationLogger.error(e.getMessage());
            }
        }
        return null;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public static class ProgramCollectionFieldName implements AbstractColumnNamesEntity {

        @Override
        public String getCollectionName() {
            return EntityColumnNames.PROGRAMS;
        }
    }

}