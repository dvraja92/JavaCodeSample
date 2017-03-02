/**
 * Created on 6/1/17 1:37 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref.graphui;

import com.decipherzone.ies.persistence.entities.graphui.Category;

public class NullCategory extends Category {
    @Override
    public boolean isNull() {
        return true;
    }
}
