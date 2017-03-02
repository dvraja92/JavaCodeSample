/**
 * Created on 6/1/17 2:59 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.graphui.Category;

import java.util.List;

public interface CategoryRepository {
    Category saveCategory(Category category) throws DatabaseOperationException;

    Category updateCategory(Category category) throws DatabaseOperationException;

    boolean deleteCategory(String id) throws DatabaseOperationException;

    boolean deleteCategory(Category category) throws DatabaseOperationException;

    List<Category> getCategoryByUICategoryObject(String categoryObjectId);

    Category getCategory(String categoryId) throws DatabaseOperationException;
}
