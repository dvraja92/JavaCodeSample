/**
 * Created on 24/12/16 4:25 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.graphui;

import com.arangodb.entity.DocumentField;
import com.arangodb.velocypack.annotations.SerializedName;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.persistence.entities.AbstractColumnNamesEntity;
import com.decipherzone.ies.persistence.entities.AbstractEntity;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "sequence")
public class Edge implements AbstractEntity, Serializable {

    @DocumentField(DocumentField.Type.ID)
    private String id;
    @DocumentField(DocumentField.Type.KEY)
    private String key;
    @DocumentField(DocumentField.Type.FROM)
    private String from;
    @DocumentField(DocumentField.Type.TO)
    private String to;
    @SerializedName(EntityColumnNames.SEQUENCE)
    private int sequence;
    @SerializedName(EntityColumnNames.PROGRAMS)
    private ArrayList<String> programs;
    @SerializedName(EntityColumnNames.ATTRIBUTES)
    private HashMap<String, Object> attributes;

    public static Edge getInstance(String from, String to, Map<?, ?> map) throws InstantiationException {
        Edge instance = getInstance(map);
        instance.setFrom(from);
        instance.setTo(to);
        return instance;
    }

    private static Map<Object, Object> formatKeys(Map<?, ?> map) {
        map.remove("abstractEntity");
        map.remove("columnsDefEntity");
        map.remove("label");
        map.remove("placeholder");

        map.remove("attributes");

        Set<?> keySet = map.keySet();
        Map<Object, Object> newMap = new HashMap<>();
        for (Object o : keySet) {
            String s = (String) o;
            newMap.put(s.toLowerCase(), map.get(s));
        }
        return newMap;
    }

    public static Edge getInstance(Map<?, ?> map) throws InstantiationException {
        Object jsonObject = map.get("attributes") == null ? map.get("attributes".toUpperCase()) : map.get("attributes");
        Map<?, ?> map1 = formatKeys(map);
        try {
            Edge edge = new Edge();
            PropertyAccessor propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(edge);
            propertyAccessor.setPropertyValues(map1);
            edge.setAttributes(convertToMap(jsonObject));
            return edge;
        } catch (Exception e) {
            ApplicationLogger.debug("Unable to instantiate Edge", e);
            throw new InstantiationException("Unable to instantiate Edge" + e.getMessage());
        }
    }

    private static HashMap<String, Object> convertToMap(Object json) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (json != null) {
                map = objectMapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {
                });
            }
        } catch (Exception e) {
//            ApplicationLogger.debug("Couldn't convert json to map", e);
            ApplicationLogger.info("Couldn't convert json to map");
        }
        return map;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
        return new EdgeCollectionField();
    }

    public ArrayList<String> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<String> programs) {
        this.programs = programs;
    }

    public HashMap<String, Object> getAttributes() {
        if(attributes == null){
            attributes = new HashMap<>();
        }
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static class EdgeCollectionField implements AbstractColumnNamesEntity {

        @Override
        public String getCollectionName() {
            return null;
        }
    }

}
