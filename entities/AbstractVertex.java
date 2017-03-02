/**
 * Created on 30/12/16 3:25 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities;

import com.decipherzone.ies.persistence.entities.graphui.Edge;

public interface AbstractVertex {
    Edge getEdge();
    String getParentId();
}
