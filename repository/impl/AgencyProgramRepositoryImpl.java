/**
 * Created on 23/12/16 6:37 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository.impl;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;
import com.decipherzone.ApplicationLogger;
import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.Agency;
import com.decipherzone.ies.persistence.entities.EntityColumnNames;
import com.decipherzone.ies.persistence.entities.Program;
import com.decipherzone.ies.persistence.entities.nullref.NullAgency;
import com.decipherzone.ies.persistence.entities.nullref.NullProgram;
import com.decipherzone.ies.persistence.repository.AgencyProgramRepository;
import com.decipherzone.ies.persistence.repository.BasicRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AgencyProgramRepositoryImpl implements AgencyProgramRepository {

    private ArangoDatabaseAsync arangoDatabaseAsync;
    private BasicRepository basicRepository;
    private ArangoDatabase arangoDatabase;

    @Inject
    public AgencyProgramRepositoryImpl(ArangoDatabaseAsync arangoDatabaseAsync, ArangoDatabase arangoDatabase) {
        this.arangoDatabaseAsync = arangoDatabaseAsync;
        this.basicRepository = BasicRepository.getInstance(arangoDatabaseAsync, arangoDatabase);
        this.arangoDatabase = arangoDatabase;
    }

    @Override
    public List<Agency> getAllAgency() {
        String query = "for a in "
                + EntityColumnNames.AGENCY
                + " return merge(a, {" + EntityColumnNames.PROGRAMS
                + ": (for p in " +
                EntityColumnNames.PROGRAMS + " for a1 in "
                + EntityColumnNames.AGENCY
                + " filter p." + EntityColumnNames.AGENCY
                + " == a1._id && a1._id == a._id return merge(p, {"
                + EntityColumnNames.AGENCYPROGRAM + ": a1}))})";
        List<Agency> agencies = new ArrayList<>();
        try {
            agencies = arangoDatabase.query(query, null, null, Agency.class).asListRemaining();
        } catch (Exception e) {
            ApplicationLogger.warn("Error while getting agencies", e);
        }

        return agencies;
    }

    //FOR doc in PROGRAMS for ag in AGENCY filter ag._id == doc.AGENCY return merge(doc, {AGENCY: ag})

    @Override
    public List<Program> getAllPrograms() {
        String query = "FOR doc in " + EntityColumnNames.PROGRAMS
                + " for ag in " + EntityColumnNames.AGENCY
                + " filter ag._id == doc." + EntityColumnNames.AGENCY
                + " return merge(doc, {" + EntityColumnNames.AGENCYPROGRAM + ": ag})";
        List<Program> programs = new ArrayList<>();
        try {
            programs = arangoDatabase.query(query, null, null, Program.class).asListRemaining();
        } catch (Exception e) {
            ApplicationLogger.warn("Error while getting programs", e);
        }

        return programs;
    }


    @Override
    public Agency saveAgency(Agency agency) throws DatabaseOperationException {
        return this.basicRepository.save(Agency.class, agency);
    }

    @Override
    public Agency updateAgency(Agency agency) throws DatabaseOperationException {
        return this.basicRepository.update(Agency.class, agency);
    }

    @Override
    public boolean deleteAgency(String agencyKey) throws DatabaseOperationException {
        boolean status;
        try {
            arangoDatabaseAsync.collection(EntityColumnNames.AGENCY).deleteDocument(agencyKey);
            arangoDatabaseAsync.query("for p in "
                    + EntityColumnNames.PROGRAMS
                    + " filter p." + EntityColumnNames.AGENCY + " == \""
                    + EntityColumnNames.AGENCY + "/"+ agencyKey
                    +"\" REMOVE p in " + EntityColumnNames.PROGRAMS, null, null, BaseDocument.class);
            status = true;
        } catch (Exception e) {
            status = false;
            ApplicationLogger.error("Error while deletion of agency by key : " + agencyKey, e);
        }
        return status;
    }

    @Override
    public boolean deleteProgram(String programKey) throws DatabaseOperationException {
        boolean status;
        try {
            arangoDatabaseAsync.collection(EntityColumnNames.PROGRAMS).deleteDocument(programKey);
            status = true;
        } catch (Exception e) {
            status = false;
            ApplicationLogger.error("Error while deletion of program by key : " + programKey, e);
        }
        return status;
    }

    @Override
    public Agency getAgencyById(String agencyId) {
        Agency agency = null;
        try {
            agency = arangoDatabase.getDocument(agencyId, Agency.class);
        } catch (Exception e) {
            ApplicationLogger.warn("Agency not found with id [" + agencyId + "]", e);
        }

        if (agency == null) {
            agency = new NullAgency();
        }

        return agency;
    }

    @Override
    public Program getProgramById(String programId) {
        Program program = null;
        String query = "FOR doc in " + EntityColumnNames.PROGRAMS
                + " for ag in " + EntityColumnNames.AGENCY
                + " filter doc._id == @programId && ag._id == doc." + EntityColumnNames.AGENCY
                + " return merge(doc, {" + EntityColumnNames.AGENCYPROGRAM + ": ag})";

        try {
            ArangoCursor<Program> cursor = arangoDatabase.query(query, new MapBuilder().put("programId", programId).get(), null, Program.class);
            if(cursor.hasNext())
                program = cursor.next();
        } catch (Exception e) {
            ApplicationLogger.warn("Program not found with id [" + programId + "]", e);
        }

        if (program == null) {
            program = new NullProgram();
        }
        return program;
    }

    @Override
    public Program saveProgram(Program program) throws DatabaseOperationException {
        return this.basicRepository.save(Program.class, program);
    }

    @Override
    public Program updateProgram(Program program) throws DatabaseOperationException {
        return this.basicRepository.update(Program.class, program);
    }

    @Override
    public List<Program> getProgramsForAgency(String agencyId) {

        List<Program> programList = new ArrayList<>();

        String query = "for programs in "+ EntityColumnNames.PROGRAMS
                + " filter programs." + EntityColumnNames.AGENCY
                + " == @agencyId return programs";

        try {
            programList = this.arangoDatabase.query(query, new MapBuilder().put("agencyId", agencyId).get(), null, Program.class).asListRemaining();
        } catch (ArangoDBException e) {
            ApplicationLogger.debug(e.getMessage(), e);
            ApplicationLogger.error(e.getMessage());
        }


        return programList;
    }
}
