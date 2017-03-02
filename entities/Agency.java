/**
 * Created on 23/12/16 5:58 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.Expose;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.List;

@DBCollection(name = EntityColumnNames.AGENCY)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class Agency implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String agencyId;

    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String agencyKey;
    @SerializedName(EntityColumnNames.AGENCYNAME)
    private String agencyName;
    @SerializedName(EntityColumnNames.SHORTNAME)
    private String shortName;
    @SerializedName(EntityColumnNames._LOGO)
    private String logo;
    @Expose(serialize = false)
    @SerializedName(EntityColumnNames.PROGRAMS)
    private List<Program> programs;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

        @Override
    public String getId() {
        return this.agencyId;
    }

    @Override
    public void setId(String id) {
        this.agencyId = id;
    }

    @Override
    public String getKey() {
        return this.agencyKey;
    }

    @Override
    public void setKey(String key) {
        this.agencyKey = key;
    }

    @Override
    @JsonIgnore
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new AgencyCollectionFieldName();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public static class AgencyCollectionFieldName implements AbstractColumnNamesEntity {


        @Override
        public String getCollectionName() {
            return EntityColumnNames.AGENCY;
        }
    }

}
