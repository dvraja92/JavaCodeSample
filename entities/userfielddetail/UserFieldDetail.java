package com.decipherzone.ies.persistence.entities.userfielddetail;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.simple.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 4/1/17 1:02 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@DBCollection(name = EntityColumnNames.USER_FIELD_DETAIL)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class UserFieldDetail implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String userFieldDetailId;
    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String userFieldDetailKey;
    @SerializedName(EntityColumnNames.MEMBER_DETAILS)
    private Map<String, Map<String, String>> memberDetails;
    @SerializedName(EntityColumnNames.USER_KEY)
    private String userKey;
    @SerializedName(EntityColumnNames.NO_OF_MEMBERS)
    private String noOfMembers;
    @SerializedName(EntityColumnNames.MEMBERS_TAX_STATUS)
    private List<Map<String, String>> memberTaxStatus;
    @SerializedName(EntityColumnNames.MEMBERS_NAMES)
    private Map<String, String> memberNames;
    @SerializedName(EntityColumnNames.MEMBER_RELATIONSHIP)
    private List<Map<String, String>> relationship;

    @Override
    public String getId() {
        return userFieldDetailId;
    }

    @Override
    public void setId(String id) {
        this.userFieldDetailId = id;
    }

    @Override
    public String getKey() {
        return userFieldDetailKey;
    }

    @Override
    public void setKey(String key) {
        this.userFieldDetailKey = key;
    }

    public Map<String, Map<String, String>> getMemberDetails() {
        if(memberDetails == null){
            memberDetails = new HashMap<>();
            memberDetails.putIfAbsent("1", new HashMap<>());
        }

        return memberDetails;
    }

    public List<Map<String, String>> getMemberTaxStatus() {
        if(memberTaxStatus == null){
            memberTaxStatus = new ArrayList<>();
        }
        return memberTaxStatus;
    }

    public Map<String, String> getMemberNames() {
        if(memberNames == null){
            memberNames = new HashMap<>();
        }
        return memberNames;
    }

    public List<Map<String, String>> getRelationship() {
        if(relationship == null){
            relationship = new ArrayList<>();
        }
        return relationship;
    }

    public void setRelationship(List<Map<String, String>> relationship) {
        this.relationship = relationship;
    }

    public void setMemberNames(Map<String, String> memberNames) {
        this.memberNames = memberNames;
    }

    public void setMemberTaxStatus(List<Map<String, String>> memberTaxStatus) {
        this.memberTaxStatus = memberTaxStatus;
    }

    public void setMemberDetails(Map<String, Map<String, String>> memberDetails) {
        this.memberDetails = memberDetails;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(String noOfMembers) {
        this.noOfMembers = noOfMembers;
    }



    public static class UserFieldDetailFieldNames implements AbstractColumnNamesEntity {

        @Override
        @JsonIgnore
        public String getCollectionName() {
            return EntityColumnNames.USER_FIELD_DETAIL;
        }
    }


    @Override
    @JsonIgnore
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new UserFieldDetailFieldNames();
    }
}
