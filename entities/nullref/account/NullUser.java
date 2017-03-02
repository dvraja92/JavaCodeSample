/**
 * Created on 30/11/16 9:05 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.entities.nullref.account;

import com.decipherzone.ies.persistence.entities.account.User;

public class NullUser extends User {
    @Override
    public boolean isNull() {
        return true;
    }
}
