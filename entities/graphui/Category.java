/**
 * Created on 6/1/17 1:29 PM by Raja Dushyant Vashishtha
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
import com.decipherzone.ies.persistence.entities.cms.UiCategory;
import com.decipherzone.ies.util.ApplicationUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.ArrayList;

@DBCollection(name = EntityColumnNames.CATEGORY)
@DBVertexCollection(name = EntityColumnNames.GRAPH_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class Category implements AbstractEntity, Serializable {

    @Expose(serialize = false, deserialize = false)
    private static final Category CATEGORY = new Category();

    public static Category getCATEGORY() {
        return CATEGORY;
    }

    @DocumentField(DocumentField.Type.ID)
    @SerializedName(EntityColumnNames._ID)
    private String id;

    @DocumentField(DocumentField.Type.KEY)
    @SerializedName(EntityColumnNames._KEY)
    private String key;

    @SerializedName(EntityColumnNames.PARENT_CATEGORY_ID)
    private String parentCategoryId;

    @SerializedName(EntityColumnNames.CATEGORY_ID)
    private String categoryId;

    @SerializedName(EntityColumnNames.UI_CATEGORY)
    @Expose(serialize = false)
    private UiCategory uiCategory;

    @Expose(serialize = false, deserialize = false)
    private Object childrenArray;

    public Category(String parentCategoryId, String categoryId) {
        this.parentCategoryId = parentCategoryId;
        this.categoryId = categoryId;
        this.setKey(ApplicationUtils.generateKey(parentCategoryId, categoryId));
    }

    public Category() {
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public UiCategory getUiCategory() {
        return uiCategory;
    }

    public void setUiCategory(UiCategory uiCategory) {
        this.uiCategory = uiCategory;
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
        return new CategoryColumnName();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Object getChildrenArray() {
        return childrenArray;
    }

    public void setChildrenArray(Object childrenArray) {
        this.childrenArray = childrenArray;
    }

    public static class CategoryColumnName implements AbstractColumnNamesEntity {
        @Override
        public String getCollectionName() {
            return EntityColumnNames.CATEGORY;
        }
    }

}
