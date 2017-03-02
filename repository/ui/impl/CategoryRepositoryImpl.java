/**
 * Created on 6/1/17 3:00 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.ui.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.util.MapBuilder;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.graphui.Category;
import com.decipherzone.ies.persistence.entities.nullref.graphui.NullCategory;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import com.decipherzone.ies.persistence.repository.ui.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{

    private ArangoDatabase arangoDatabase;
    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;

    @Inject
    public CategoryRepositoryImpl(ArangoDatabase arangoDatabase, ArangoDatabaseAsync arangoDatabaseAsync) {
        this.arangoDatabase = arangoDatabase;
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
    }

    @Override
    public Category saveCategory(Category category) throws DatabaseOperationException {
        return this.basicRepository.save(Category.class, category);
    }

    @Override
    public Category updateCategory(Category category) throws DatabaseOperationException {
        return this.basicRepository.update(Category.class, category);
    }

    @Override
    public boolean deleteCategory(String id){
        boolean status = false;
        try {
            this.deleteCategory(this.getCategory(id));
            status = true;
        } catch (DatabaseOperationException e) {
            ApplicationLogger.warn("Unable to delete category");
            ApplicationLogger.debug("Unable to delete category", e);
        }
        return status;
    }

    @Override
    public boolean deleteCategory(Category category){
        boolean status = false;
        try {
            this.basicRepository.delete(category);
            status = true;
        } catch (DatabaseOperationException e) {
            ApplicationLogger.warn("Unable to delete category");
            ApplicationLogger.debug("Unable to delete category", e);
        }
        return status;
    }

    @Override
    public List<Category> getCategoryByUICategoryObject(String categoryObjectId){
        try {
            String query = "for doc in @@collectionName for f in @@categoryObjectCollectionName filter f._id == doc."
                    + EntityColumnNames.CATEGORY_ID
                    + " && doc."+EntityColumnNames.CATEGORY_ID+" == @categoryId return merge(doc, {"
                    + EntityColumnNames.UI_CATEGORY
                    + ": f})";

            ApplicationLogger.debug(query);

            Map<String, Object> map = new MapBuilder()
                    .put("@collectionName", EntityColumnNames.CATEGORY)
                    .put("categoryId", categoryObjectId)
                    .put("@categoryObjectCollectionName", EntityColumnNames.UI_CATEGORY).get();
            ArangoCursor<Category> cursor = this.arangoDatabase.query(query, map, null, Category.class);
            return cursor.asListRemaining();
        } catch (Exception e) {
            ApplicationLogger.warn("No category is available with this id", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Category getCategory(String categoryId) throws DatabaseOperationException {
        try {
            String query = "for doc in @@collectionName for f in @@categoryObjectCollectionName filter f._id == doc."
                    + EntityColumnNames.CATEGORY_ID
                    + " && doc._id == @id return merge(doc, {"
                    + EntityColumnNames.UI_CATEGORY
                    + ": f})";

            ApplicationLogger.debug(query);

            Map<String, Object> map = new MapBuilder()
                    .put("@collectionName", EntityColumnNames.CATEGORY)
                    .put("id", categoryId)
                    .put("@categoryObjectCollectionName", EntityColumnNames.UI_CATEGORY).get();
            ArangoCursor<Category> cursor = this.arangoDatabase.query(query, map, null, Category.class);
            if (cursor.hasNext()) {
                return cursor.next();
            }

        } catch (Exception e) {
            ApplicationLogger.warn("No category is available with this id", e);
        }

        return new NullCategory();

    }
}
