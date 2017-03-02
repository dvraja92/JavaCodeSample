/**
 * Created on 2/1/17 11:33 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.graphui.Field;

import java.util.List;

public interface FieldRepository {
    Field saveField(Field field) throws DatabaseOperationException;

    Field updateField(Field field) throws DatabaseOperationException;

    boolean deleteField(String id) throws DatabaseOperationException;

    boolean deleteField(Field fieldId) throws DatabaseOperationException;

    Field getField(String fieldId) throws DatabaseOperationException;

    List<Field> getAllFields(String programKey);
}
