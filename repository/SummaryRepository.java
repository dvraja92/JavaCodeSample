package com.decipherzone.ies.persistence.repository;

import com.decipherzone.ies.persistence.entities.graphui.SummaryVertex;

import java.util.List;

/**
 * Created on 20/1/17 5:29 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public interface SummaryRepository {

    <T> List<T> getSummaryDetails(String[] startVertexIds, int depth, Object filters, Class<T> claaz);

}
