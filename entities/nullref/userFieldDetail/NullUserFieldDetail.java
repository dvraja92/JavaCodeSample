package com.decipherzone.ies.persistence.entities.nullref.userFieldDetail;

import com.decipherzone.ies.persistence.entities.userfielddetail.UserFieldDetail;

/**
 * Created on 4/1/17 1:38 AM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public class NullUserFieldDetail extends UserFieldDetail {

    @Override
    public boolean isNull() {
        return true;
    }
}
