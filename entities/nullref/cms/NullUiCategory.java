package com.decipherzone.ies.persistence.entities.nullref.cms;

import com.decipherzone.ies.persistence.entities.cms.UiCategory;

/**
 * Created on 17/12/16 2:20 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public class NullUiCategory extends UiCategory {
    @Override
    public boolean isNull() {
        return true;
    }

}
