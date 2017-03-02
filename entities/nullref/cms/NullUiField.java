package com.decipherzone.ies.persistence.entities.nullref.cms;

import com.decipherzone.ies.persistence.entities.cms.UiField;

/**
 * Created on 23/12/16 11:10 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public class NullUiField extends UiField {

    @Override
    public boolean isNull() {
        return true;
    }
}
