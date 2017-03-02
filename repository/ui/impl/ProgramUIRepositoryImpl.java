/**
 * Created on 24/12/16 3:45 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.EdgeUpdateEntity;
import com.arangodb.util.MapBuilder;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.error.EdgeNotFoundException;
import com.decipherzone.ies.persistence.entities.graphui.Edge;
import com.decipherzone.ies.persistence.entities.nullref.graphui.NullEdge;
import com.decipherzone.ies.persistence.enums.EdgeType;
import com.decipherzone.ies.persistence.repository.ui.ProgramUIRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProgramUIRepositoryImpl implements ProgramUIRepository {

    private ArangoDatabase arangoDatabase;
    private ArangoDatabaseAsync arangoDatabaseAsync;

    @Inject
    public ProgramUIRepositoryImpl(ArangoDatabase arangoDatabase, ArangoDatabaseAsync arangoDatabaseAsync) {
        this.arangoDatabase = arangoDatabase;
        this.arangoDatabaseAsync = arangoDatabaseAsync;
    }

    @Override
    public <T> List<T> getGraphUI(String[] startVertexIds, int depth, Object filters, Class<T> claaz) {
        List<T> list = null;
        String query = "for vertex in @startVertex FOR v,e,p IN 1..@depth OUTBOUND vertex GRAPH 'programs' OPTIONS {bfs: true} return merge(v, {EDGE: e, PARENT: e._from})";
        Map<String, Object> map = new MapBuilder().put("depth", depth).put("startVertex", startVertexIds).get();
        try {
            list = this.arangoDatabase.query(query, map, null, claaz).asListRemaining();
        } catch (Exception e) {
            ApplicationLogger.warn("Couldn't get data from Graph", e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public <T> List<T> getGraphEdges(String[] startVertexIds, int depth, Object filters, Class<T> claaz) {
        List<T> list = null;
        String query = "for vertex in @startVertex FOR v,e,p IN 1..@depth OUTBOUND vertex GRAPH 'programs' OPTIONS {bfs: true} return e";
        Map<String, Object> map = new MapBuilder().put("depth", depth).put("startVertex", startVertexIds).get();
        try {
            list = this.arangoDatabase.query(query, map, null, claaz).asListRemaining();
        } catch (Exception e) {
            ApplicationLogger.warn("Couldn't get data from Graph", e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public boolean saveEdge(EdgeType edgeType, Edge edge, String programKey) {
        boolean status = false;
        try {
            Edge dbEdge = this.getEdge(edgeType, edge.getFrom(), edge.getTo());
            if (!dbEdge.isNull()){
                status = this.updateEdge(edgeType, edge, programKey);
            }else{
                this.arangoDatabase.graph("programs").edgeCollection(edgeType.name()).insertEdge(edge);
                status = true;
            }
        } catch (DatabaseOperationException e) {
            ApplicationLogger.warn("Unable to update edge", e);
        }
        return status;
    }

    @Override
    public boolean updateEdge(EdgeType edgeType, Edge edge, String programKey) {
        boolean status = false;
        try {
            Edge dbEdge = this.getEdge(edgeType, edge.getFrom(), edge.getTo());
            if(dbEdge.isNull()){
                throw new EdgeNotFoundException("Edge is not available (" + edgeType.getEdgeTypeStr() + " from: " + edge.getFrom() + ", to: " + edge.getTo());
            }
            ArrayList<String> programs = dbEdge.getPrograms();
            if(programs == null)
                programs = new ArrayList<>();
            if (!programs.contains(programKey)) {
                programs.add(programKey);
            }
            edge.setPrograms(programs);
            String key = dbEdge.getKey();
            if (key != null) {
                this.arangoDatabase.graph("programs").edgeCollection(edgeType.getEdgeTypeStr()).updateEdge(key, edge);
                status = true;
            }
        } catch (EdgeNotFoundException | DatabaseOperationException e) {
            ApplicationLogger.warn("Unable to update edge");
            ApplicationLogger.debug("Unable to update edge", e);
        }
        return status;
    }

    @Override
    public boolean removeEdge(EdgeType edgeType, Edge edge, String programKey) {
        boolean status = false;
        try {
            String key;
            Edge dbEdge = this.getEdge(edgeType, edge.getFrom(), edge.getTo());
            if(dbEdge.isNull()){
                throw new EdgeNotFoundException("Edge is not available");
            }
            ArrayList<String> programs = dbEdge.getPrograms();
            if (programs != null && programs.size() > 1) {
                programs.remove(programKey);
                this.arangoDatabase.graph("programs").edgeCollection(edgeType.getEdgeTypeStr()).replaceEdge(dbEdge.getKey(), dbEdge);
                status = true;
            } else {
                key = dbEdge.getKey();
                if (key != null) {
                    this.arangoDatabase.graph("programs").edgeCollection(edgeType.getEdgeTypeStr()).deleteEdge(key);
                    status = true;
                }
            }
        } catch (Exception e) {
            ApplicationLogger.debug("Unable to remove edge", e);
            ApplicationLogger.warn("Unable to remove edge");
        }
        return status;
    }

    @Override
    public Edge getEdge(EdgeType edgeType, String from, String to) throws DatabaseOperationException {
        String query = "for e in " + edgeType.getEdgeTypeStr() + " filter e._from == @from && e._to == @to return e";
        Map<String, Object> map = new MapBuilder().put("from", from).put("to", to).get();
        try {
            ArangoCursor<Edge> cursor = this.arangoDatabase.query(query, map, null, Edge.class);
            if (cursor.hasNext())
                return cursor.next();
        } catch (Exception e) {
            ApplicationLogger.debug("Error while executing query : " + query);
            ApplicationLogger.debug("with supplied data " + map);
            throw new DatabaseOperationException("Error while getting edge", e);
        }

        return new NullEdge();
    }

    @Override
    public void updateEdgesForCopiedProgram(List<BaseEdgeDocument> baseEdgeDocumentList) {
        for (BaseEdgeDocument baseEdgeDocument : baseEdgeDocumentList) {
            String CollectionName = baseEdgeDocument.getId().split("/")[0];
            arangoDatabaseAsync.collection(CollectionName).updateDocument(baseEdgeDocument.getKey(), baseEdgeDocument);
        }
    }
}