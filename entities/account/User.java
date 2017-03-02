package com.decipherzone.ies.persistence.entities.account;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.annotation.DBIndex;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.enums.RoleType;
import com.decipherzone.ies.persistence.enums.SecurityQuestion;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;

/**
 * Created by raja on 29/10/16.
 */
@DBCollection(name = EntityColumnNames.USERS)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="sequence")
public class User implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String key;
    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String userid;
    @SerializedName(EntityColumnNames.USERNAME)
    @DBIndex
    private String userName;
    @SerializedName(EntityColumnNames.EMAIL)
    private String email;
    @SerializedName(EntityColumnNames.PASSWORD)
    @JsonIgnore
    private String password;
    @SerializedName(EntityColumnNames.IS_ENABLED)
    private boolean enabled;
    @SerializedName(EntityColumnNames.SECURITY_QUESTION)
    private SecurityQuestion securityQuestion;
    @SerializedName(EntityColumnNames.SECURITY_QUESTION_ANSWER)
    private String securityQuestionAnswer;
    @SerializedName(EntityColumnNames.ROLE)
    private RoleType roleType;
    @SerializedName(EntityColumnNames.SELECTED_PROGRAM)
    private List<String> selectedPrograms;
    @SerializedName(EntityColumnNames.AGENCY_KEY)
    private String agencyKey;
    @SerializedName(EntityColumnNames.PROGRAM_KEY)
    private String programKey;

    @Override
    public String getId() {
        return this.userid;
    }

    @Override
    public void setId(String id) {
        this.userid = id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public int getSecurityQuestionBit(){
        return this.securityQuestion.getBit();
    }


    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public String getRoleTypeStr(){
        return roleType.getRoleStr();
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<String> getSelectedPrograms() {
        return selectedPrograms;
    }

    public void setSelectedPrograms(List<String> selectedPrograms) {
        this.selectedPrograms = selectedPrograms;
    }

    public String getAgencyKey() {
        return agencyKey;
    }

    public void setAgencyKey(String agencyKey) {
        this.agencyKey = agencyKey;
    }

    public String getProgramKey() {
        return programKey;
    }

    public void setProgramKey(String programKey) {
        this.programKey = programKey;
    }

    public static class UserTableColumns implements AbstractColumnNamesEntity {


        @Override
        public String getCollectionName() {
            return EntityColumnNames.USERS;
        }
    }

    @Override
    @JsonIgnore
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new UserTableColumns();
    }
}
