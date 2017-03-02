/**
 * Created on 24/12/16 3:45 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui;

import com.arangodb.entity.BaseEdgeDocument;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.graphui.Edge;
import com.decipherzone.ies.persistence.enums.EdgeType;

import java.util.List;
import java.util.Map;

public interface ProgramUIRepository {
    <T> List<T> getGraphUI(String[] startVertexIds, int depth, Object filters, Class<T> claaz);
    <T> List<T> getGraphEdges(String[] startVertexIds, int depth, Object filters, Class<T> claaz);

    boolean saveEdge(EdgeType edgeType, Edge instance, String programKey);
    boolean updateEdge(EdgeType edgeType, Edge edge, String programKey);
    boolean removeEdge(EdgeType edgeType, Edge edge, String programKey);

    Edge getEdge(EdgeType edgeType, String from, String to) throws DatabaseOperationException;

    void updateEdgesForCopiedProgram(List<BaseEdgeDocument> baseEdgeDocumentList);
}