package com.decipherzone.ies.persistence.repository.impl;

import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.util.MapBuilder;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.persistence.repository.SummaryRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 20/1/17 5:36 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Repository
public class SummaryRepositoryImpl implements SummaryRepository {

    private ArangoDatabase arangoDatabase;
    private ArangoDatabaseAsync arangoDatabaseAsync;

    @Inject
    public SummaryRepositoryImpl(ArangoDatabase arangoDatabase, ArangoDatabaseAsync arangoDatabaseAsync) {
        this.arangoDatabase = arangoDatabase;
        this.arangoDatabaseAsync = arangoDatabaseAsync;
    }

    @Override
    public <T> List<T> getSummaryDetails(String[] startVertexIds, int depth, Object filters, Class<T> claaz) {
        List<T> list = null;
        String query = "for vertex in @startVertex FOR v,e,p IN 1..@depth OUTBOUND vertex GRAPH 'programs' OPTIONS {bfs: true} return merge(v, {EDGE: e, PARENT: e._from, FIELDOBJ: (for dd in UI_FIELD_OBJECTS filter dd._id == v.FIELD_ID return dd), CATOBJ: (for d2 in UI_CATEGORIZATION filter d2._id == v.CATEGORY_ID return d2)})";
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
}
