/**
 * Created on 28/11/16 11:21 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.init;

import com.decipherzone.Application;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.ApplicationInitializationException;
import com.decipherzone.ies.error.CategoryAlreadyExistException;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.error.FieldAlreadyExistException;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.account.User;
import com.decipherzone.ies.persistence.entities.cms.UiCategory;
import com.decipherzone.ies.persistence.entities.cms.UiField;
import com.decipherzone.ies.persistence.entities.graphui.Category;
import com.decipherzone.ies.persistence.entities.graphui.Field;
import com.decipherzone.ies.persistence.enums.RoleType;
import com.decipherzone.ies.persistence.enums.SecurityQuestion;
import com.decipherzone.ies.persistence.enums.UiCategorizationType;
import com.decipherzone.ies.persistence.enums.UiFieldType;
import com.decipherzone.ies.persistence.repository.CollectionLoader;
import com.decipherzone.ies.service.DummyDataCreationService;
import com.decipherzone.ies.service.account.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private DummyDataCreationService dummyDataCreationService;
    private CollectionLoader collectionLoader;

    @Inject
    public InitialDataLoader(UserService userService, PasswordEncoder passwordEncoder, DummyDataCreationService dummyDataCreationService, CollectionLoader collectionLoader) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.dummyDataCreationService = dummyDataCreationService;
        this.collectionLoader = collectionLoader;
    }

    /**
     * onApplicationEvent : which will be invoked when Context is refreshed
     * Initial context based data loading will be done here
     *
     * @param contextRefreshedEvent ContextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationLogger.debug("Application Event is refreshed and method invoked");
        if (alreadySetup)
            return;

        //  initializing dummy file to be used when the file is not available for any component
        try {
            Application.NO_IMAGE_FILE = contextRefreshedEvent.getApplicationContext().getResource("/resources/images/dummy/no-image-box.png").getFile();

        } catch (IOException e) {
            ApplicationLogger.error("Error while getting dummy file ", e);
        }

        try {
            initDatabase();
            initForAdminUser();
            try {
                initForHouseholdMaster();
            } catch (FieldAlreadyExistException | CategoryAlreadyExistException e) {
                ApplicationLogger.debug(e.getMessage(), e);
                ApplicationLogger.error(e.getMessage());
            }

            alreadySetup = true;
        } catch (ApplicationInitializationException e) {
            ApplicationLogger.error("Shutting down application", e);
            ApplicationLogger.error("failed to start due to startup errors : ", e);
            ((ConfigurableApplicationContext) contextRefreshedEvent.getApplicationContext()).close();
        }
    }

    private void initDatabase() throws ApplicationInitializationException {
        this.collectionLoader.loadCollections();
    }

    private void initForAdminUser() {
        //  On Fresh deployment of the application inserting the fresh admin user to process
        if (userService.getUserByUsername("admin").isNull()) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEnabled(true);
            user.setRoleType(RoleType.ROLE_SUPER_ADMIN);
            user.setEmail("admin@ies.com");
            user.setSecurityQuestion(SecurityQuestion.What_was_the_make_and_model_of_your_first_car);
            user.setSecurityQuestionAnswer("d3c1ph3r@92");
            try {
                userService.saveUser(user);
            } catch (DatabaseOperationException e) {
                ApplicationLogger.error("", e);
            }
        }
    }


    private void initForHouseholdMaster() throws FieldAlreadyExistException, CategoryAlreadyExistException {

        UiField uiHouseholdMembersOptionGroup = dummyDataCreationService.createUiField("How many members are there in your household?", "MASTER", UiFieldType.OPTION_GROUP);
        UiField uiFieldOption2 = dummyDataCreationService.createUiField("2", "NORMAL", UiFieldType.OPTION);
        UiField uiFieldOption3 = dummyDataCreationService.createUiField("3", "NORMAL", UiFieldType.OPTION);
        UiField uiFieldOption4 = dummyDataCreationService.createUiField("4", "NORMAL", UiFieldType.OPTION);
        UiField uiFieldOption5 = dummyDataCreationService.createUiField("5", "NORMAL", UiFieldType.OPTION);
        UiField uiFieldOption6 = dummyDataCreationService.createUiField("6", "NORMAL", UiFieldType.OPTION);

        UiCategory uiCategoryForHousehold = dummyDataCreationService.createUiCategory(EntityColumnNames.HOUSEHOLD_MEMBERS, UiCategorizationType.CATEGORY);

        //  Creating relations for household members section
        Category categoryForHousehold = dummyDataCreationService.createCategory("", uiCategoryForHousehold.getId());
        Field householdGroup = dummyDataCreationService.createField(categoryForHousehold.getId(), uiHouseholdMembersOptionGroup.getId(), "", "");
        dummyDataCreationService.createField(householdGroup.getId(), uiFieldOption2.getId(), "", "");
        dummyDataCreationService.createField(householdGroup.getId(), uiFieldOption3.getId(), "", "");
        dummyDataCreationService.createField(householdGroup.getId(), uiFieldOption4.getId(), "", "");
        dummyDataCreationService.createField(householdGroup.getId(), uiFieldOption5.getId(), "", "");
        dummyDataCreationService.createField(householdGroup.getId(), uiFieldOption6.getId(), "", "");

    }
}
