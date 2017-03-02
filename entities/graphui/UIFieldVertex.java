/**
 * Created on 31/12/16 6:28 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.graphui;

import com.arangodb.velocypack.annotations.Expose;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractVertex;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.cms.UiCategory;

import java.io.Serializable;
import java.util.List;

public class UIFieldVertex extends Field implements AbstractVertex, Serializable {
    @SerializedName(EntityColumnNames.EDGE)
    private Edge edge;
    @SerializedName("PARENT")
    private String parentId;

    @Expose(serialize = false, deserialize = false)
    private List<UICategoryVertex> children;

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    @Override
    public AbstractColumnNamesEntity getColumnsDefEntity() {
        return new Field.FieldColumnName();
    }

    public List<UICategoryVertex> getChildren() {
        return children;
    }

    public void setChildren(List<UICategoryVertex> children) {
        this.children = children;
    }
}
