/**
 * Created on 23/12/16 6:31 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref;

import com.decipherzone.ies.persistence.entities.Agency;

public class NullAgency extends Agency {
    @Override
    public boolean isNull() {
        return true;
    }
}
