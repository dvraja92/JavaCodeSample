/**
 * Created on 2/1/17 10:16 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.graphui;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.Expose;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.annotation.DBVertexCollection;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.cms.UiField;
import com.decipherzone.ies.util.ApplicationUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

@DBCollection(name = EntityColumnNames.FIELD)
@DBVertexCollection(name = EntityColumnNames.GRAPH_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class Field implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String id;

    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String key;

    @SerializedName(EntityColumnNames.FIELD_ID)
    private String fieldObjectId;

    @SerializedName(EntityColumnNames.FIELD)
    @Expose(serialize = false)
    private UiField field;

    @SerializedName(EntityColumnNames.FIELD_LABEL)
    private String label;

    @SerializedName(EntityColumnNames.FIELD_NAME)
    private String fieldName;

    @SerializedName(EntityColumnNames.FIELD_PLACEHOLDER)
    private String placeholder;

    @SerializedName(EntityColumnNames.PARENT)
    private String parentId;

    private String fieldValue;

    public Field(String parentId, String fieldObjectId, String label, String placeholder) {
        this.parentId = parentId;
        this.fieldObjectId = fieldObjectId;
        this.label = label;
        this.placeholder = placeholder;
        this.fieldName = this.label.replaceAll("[^a-zA-z0-9]", "");
        setKey(ApplicationUtils.generateKey(parentId, fieldObjectId, label));
    }

    public Field() {
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldObjectId() {
        return fieldObjectId;
    }

    public void setFieldObjectId(String fieldObjectId) {
        this.fieldObjectId = fieldObjectId;
        this.key = this.key + fieldObjectId.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    public UiField getField() {
        return field;
    }

    public void setField(UiField field) {
        this.field = field;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new FieldColumnName();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public static class FieldColumnName implements AbstractColumnNamesEntity {


        @Override
        public String getCollectionName() {
            return EntityColumnNames.FIELD;
        }
    }

}