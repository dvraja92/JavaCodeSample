/**
 * Created on 2/1/17 11:51 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref.graphui;

import com.decipherzone.ies.persistence.entities.graphui.Field;

public class NullField extends Field {
    @Override
    public boolean isNull() {
        return true;
    }
}
