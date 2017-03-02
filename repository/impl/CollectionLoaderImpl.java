/**
 * Created on 19/1/17 11:35 AM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.impl;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoGraph;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.model.HashIndexOptions;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.Application;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.annotation.DBCollection;
import com.decipherzone.ies.annotation.DBIndex;
import com.decipherzone.ies.annotation.DBVertexCollection;
import com.decipherzone.ies.error.ApplicationInitializationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.enums.EdgeType;
import com.decipherzone.ies.persistence.repository.CollectionLoader;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollectionLoaderImpl implements CollectionLoader {

    private ArangoDatabase arangoDatabase;

    @Inject
    public CollectionLoaderImpl(ArangoDatabase arangoDatabase) {
        this.arangoDatabase = arangoDatabase;
    }

    @Override
    public void loadCollections() throws ApplicationInitializationException {
        Map<String, Class> collectionNames = new HashMap<>();
        Map<String, String> vertexCollectionNames = new HashMap<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DBCollection.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(Application.BASE_PACKAGE)) {
            try {
                Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
                collectionNames.put(aClass.getAnnotation(DBCollection.class).name(), aClass);
                if (aClass.isAnnotationPresent(DBVertexCollection.class)) {
                    String vCollectionName = aClass.getAnnotation(DBVertexCollection.class).name();
                    if(vCollectionName.length() == 0)
                        vCollectionName = aClass.getAnnotation(DBCollection.class).name();
                    vertexCollectionNames.put(aClass.getAnnotation(DBCollection.class).name(), vCollectionName);
                }
            } catch (ClassNotFoundException e) {
                ApplicationLogger.debug("Class not found", e);
                ApplicationLogger.warn("Unable to find class : " + beanDefinition.getBeanClassName());
            } catch (Exception e) {
                ApplicationLogger.debug("Unable to get collection name", e);
                ApplicationLogger.warn("Unable to get collection name: " + beanDefinition.getBeanClassName());
            }
        }

        for (String key : collectionNames.keySet()) {
            if (!checkIfCollectionExists(key)) {
                createCollection(key, collectionNames.get(key));
            }
        }

        try {
            this.createGraphIfAbsent(EntityColumnNames.GRAPH_NAME, vertexCollectionNames);
        } catch (Exception e) {
            ApplicationLogger.error("Unable to create graph", e);
            throw new ApplicationInitializationException(e);
        }

    }

    private boolean checkIfCollectionExists(String collectionName) {
        try {
            ArangoCollection collection = this.arangoDatabase.collection(collectionName);
            if (collection == null) {
                return false;
            } else {
                collection.getInfo();
                return true;
            }
        } catch (Exception e) {
            ApplicationLogger.debug("", e);
            ApplicationLogger.info("Collection : " + collectionName + " does not exists");
        }
        return false;
    }

    private void createCollection(String collectionName, Class collectionClazz) {
        this.arangoDatabase.createCollection(collectionName);
        ArangoCollection arangoCollection = this.arangoDatabase.collection(collectionName);
        Field[] fields = collectionClazz.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DBIndex.class)) {
                DBIndex index = field.getAnnotation(DBIndex.class);
                String dbFieldName;
                if (field.isAnnotationPresent(SerializedName.class)) {
                    dbFieldName = field.getAnnotation(SerializedName.class).value();
                } else {
                    dbFieldName = field.getName();
                }

                dbFieldName = dbFieldName.concat(index.pattern().toString());

                for (DBIndex.Index index1 : index.type()){
                    this.createIndex(arangoCollection, index1, dbFieldName);
                }
            }
        }
    }

    private void createGraphIfAbsent(String graphName, Map<String, String> vertexCollectionNames) throws ClassNotFoundException {
        boolean isAvailable = true;
        try {
            ArangoGraph graph = this.arangoDatabase.graph(graphName);
            if (graph == null) {
                isAvailable = false;
            } else {
                graph.getInfo();
            }
        } catch (Exception e) {
            ApplicationLogger.debug("", e);
            ApplicationLogger.info("Graph : " + graphName + " does not exists");
            isAvailable = false;
        }

        if (!isAvailable) {
            EdgeType[] edgeTypes = EdgeType.values();
            Collection<EdgeDefinition> edgeDefinitions = new ArrayList<>();
            for (EdgeType edgeType : edgeTypes) {
                EdgeDefinition edgeDefinition = new EdgeDefinition();
                edgeDefinition.collection(edgeType.getEdgeTypeStr());
                edgeDefinition.from(Class.forName(edgeType.getFrom()).getAnnotation(DBCollection.class).name());
                edgeDefinition.to(Class.forName(edgeType.getTo()).getAnnotation(DBCollection.class).name());
                edgeDefinitions.add(edgeDefinition);
            }
            this.arangoDatabase.createGraph(graphName, edgeDefinitions);
            for (String s : vertexCollectionNames.keySet()) {
                if (graphName.equalsIgnoreCase(vertexCollectionNames.get(s)))
                    this.arangoDatabase.graph(graphName).addVertexCollection(s);
            }
        }
    }

    private void createIndex(ArangoCollection collection, DBIndex.Index index, String... dbFieldName) {
        List<String> list = Arrays.asList(dbFieldName);
        switch (index) {
            case FULLTEXT:
                collection.createFulltextIndex(list, null);
                break;
            case HASH:
                HashIndexOptions hashIndexOptions = new HashIndexOptions();
                hashIndexOptions.unique(false);
                collection.createHashIndex(list, hashIndexOptions);
                break;
            case UNIQUE_HASH:
                HashIndexOptions uniqueHashIndexOptions = new HashIndexOptions();
                uniqueHashIndexOptions.unique(true);
                collection.createHashIndex(list, uniqueHashIndexOptions);
                break;
            case GEO:
                collection.createGeoIndex(list, null);
                break;
            default:
                ApplicationLogger.warn("Unknown Index " + index);
        }
    }

}
