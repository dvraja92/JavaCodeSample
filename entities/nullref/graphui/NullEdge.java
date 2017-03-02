/**
 * Created on 4/1/17 2:06 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref.graphui;

import com.decipherzone.ies.persistence.entities.graphui.Edge;

public class NullEdge extends Edge {
    @Override
    public boolean isNull() {
        return true;
    }
}
