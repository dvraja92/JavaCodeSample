/**
 * Created on 19/1/17 11:34 AM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository;

import com.decipherzone.ies.error.ApplicationInitializationException;

public interface CollectionLoader {
    void loadCollections() throws ApplicationInitializationException;
}
