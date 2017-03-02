/**
 * Created on 24/12/16 4:10 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.graphui;

import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.AbstractVertex;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class UICategoryVertex extends Category implements AbstractVertex, AbstractEntity, Serializable {

    @SerializedName(EntityColumnNames.EDGE)
    private Edge edge;
    @SerializedName("PARENT")
    private String parentId;

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
        return new Category().getColumnsDefEntity();
    }
}
