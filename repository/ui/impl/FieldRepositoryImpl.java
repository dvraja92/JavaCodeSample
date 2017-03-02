/**
 * Created on 2/1/17 11:34 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.util.MapBuilder;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.graphui.Field;
import com.decipherzone.ies.persistence.entities.nullref.graphui.NullField;
import com.decipherzone.ies.persistence.enums.EdgeType;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.ui.FieldRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FieldRepositoryImpl implements FieldRepository {

    private ArangoDatabase arangoDatabase;
    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;

    @Inject
    public FieldRepositoryImpl(ArangoDatabase arangoDatabase, ArangoDatabaseAsync arangoDatabaseAsync) {
        this.arangoDatabase = arangoDatabase;
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
    }

    @Override
    public Field saveField(Field field) throws DatabaseOperationException {
        return this.basicRepository.save(Field.class, field);
    }

    @Override
    public Field updateField(Field field) throws DatabaseOperationException {
        return this.basicRepository.update(Field.class, field);
    }

    @Override
    public Field getField(String fieldId) throws DatabaseOperationException {

        try {
            String query = "for doc in @@collectionName for f in @@fieldObjectCollectionName filter f._id == doc."
                    + EntityColumnNames.FIELD_ID
                    + " && doc._id == @id return merge(doc, {"
                    + EntityColumnNames.FIELD
                    + ": f})";

            ApplicationLogger.debug(query);

            Map<String, Object> map = new MapBuilder()
                    .put("@collectionName", EntityColumnNames.FIELD)
                    .put("id", fieldId)
                    .put("@fieldObjectCollectionName", EntityColumnNames.UI_FIELD).get();
            ArangoCursor<Field> cursor = this.arangoDatabase.query(query, map, null, Field.class);
            if (cursor.hasNext()) {
                return cursor.next();
            }

        } catch (Exception e) {
            ApplicationLogger.warn("No field is available with this id", e);
        }

        return new NullField();
    }

    @Override
    public boolean deleteField(String id) throws DatabaseOperationException {
        return this.deleteField(this.getField(id));
    }

    @Override
    public boolean deleteField(Field field) {
        boolean status = false;
        try {
            this.basicRepository.delete(field);
            status = true;
        } catch (DatabaseOperationException e) {
            ApplicationLogger.warn("Unable to delete Field", e);
        }
        return status;
    }

    @Override
    public List<Field> getAllFields(String programKey) {
        try {
            String query = "FOR doc IN UNION_DISTINCT ((for a1 in @@collectionUICAT for a2 in @@field for a3 in @@uiFieldObject filter @programKey in a1."
                    + EntityColumnNames.PROGRAMS
                    + " && a2._id == a1._to && a3._id == a2."
                    + EntityColumnNames.FIELD_ID
                    + " return merge(a2, { "
                    + EntityColumnNames.EDGE
                    + ": a1, " + EntityColumnNames.FIELD + ": a3 })), (for b1 in @@collectionGroupFields for b2 in @@field for b3 in @@uiFieldObject filter @programKey in b1."
                    + EntityColumnNames.PROGRAMS
                    + " && b2._id == b1._to && b3._id == b2."
                    + EntityColumnNames.FIELD_ID
                    + " return merge(b2, { "
                    + EntityColumnNames.EDGE
                    + ": b1, "
                    + EntityColumnNames.FIELD
                    + ": b3 }))) RETURN doc";
            Map<String, Object> map = new MapBuilder()
                    .put("@collectionUICAT", EdgeType.UI_CAT_FIELDS.getEdgeTypeStr())
                    .put("@collectionGroupFields", EdgeType.GROUP_FIELDS.getEdgeTypeStr())
                    .put("@field", EntityColumnNames.FIELD)
                    .put("@uiFieldObject", EntityColumnNames.UI_FIELD)
                    .put("programKey", programKey).get();
            ArangoCursor<Field> cursor = this.arangoDatabase.query(query, map, null, Field.class);
            if (cursor.hasNext()) {
                return cursor.asListRemaining();
            }
        } catch (ArangoDBException e) {
            ApplicationLogger.debug("Unable to get program fields", e);
            ApplicationLogger.warn("Unable to get program fields");
        }
        return new ArrayList<>();
    }

}