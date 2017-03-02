package com.decipherzone.ies.persistence.entities.graphui;


import com.decipherzone.ies.persistence.entities.AbstractVertex;

/**
 * Created on 20/1/17 12:34 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public class SummaryVertex implements AbstractVertex  {

    private Edge edge;
    private String parentId;
    private boolean isCategory;
    private boolean isField;
    private UICategoryVertex uiCategoryVertex;
    private UIFieldVertex uiFieldVertex;


    @Override
    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    public boolean isField() {
        return isField;
    }

    public void setField(boolean field) {
        isField = field;
    }

    public UICategoryVertex getUiCategoryVertex() {
        return uiCategoryVertex;
    }

    public void setUiCategoryVertex(UICategoryVertex uiCategoryVertex) {
        this.uiCategoryVertex = uiCategoryVertex;
    }

    public UIFieldVertex getUiFieldVertex() {
        return uiFieldVertex;
    }

    public void setUiFieldVertex(UIFieldVertex uiFieldVertex) {
        this.uiFieldVertex = uiFieldVertex;
    }
}
