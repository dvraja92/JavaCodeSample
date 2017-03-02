/**
 * Created on 23/12/16 6:32 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref;

import com.decipherzone.ies.persistence.entities.Program;

public class NullProgram extends Program {
    @Override
    public boolean isNull() {
        return true;
    }
}
