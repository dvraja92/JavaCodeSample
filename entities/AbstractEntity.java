/**
 * Created on 29/11/16 7:34 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities;

public interface AbstractEntity {

    static boolean isNull(Object o) {
        return o == null;
    }

    default boolean isNull() {
        return false;
    }

    default AbstractEntity getAbstractEntity() {
        return this;
    }

    String getId();

    void setId(String id);

    String getKey();

    void setKey(String key);

    AbstractColumnNamesEntity getColumnsDefEntity();

}
