/**
 * Created on 23/12/16 6:33 PM by Raja Dushyant Vashishtha
 * Sr. Software Engineer
 * rajad@decipherzone.com
 * Decipher Zone Softwares
 * www.decipherzone.com
 */

package com.decipherzone.ies.persistence.repository;

import com.decipherzone.ies.error.DatabaseOperationException;
import com.decipherzone.ies.persistence.entities.Agency;
import com.decipherzone.ies.persistence.entities.Program;

import java.util.List;

public interface AgencyProgramRepository {
    List<Agency> getAllAgency();

    boolean deleteAgency(String agencyKey) throws DatabaseOperationException;

    boolean deleteProgram(String programKey) throws DatabaseOperationException;

    Agency getAgencyById(String agencyId);

    Agency saveAgency(Agency agency) throws DatabaseOperationException;

    Agency updateAgency(Agency agency) throws DatabaseOperationException;

    List<Program> getAllPrograms();

    Program getProgramById(String programId);

    Program saveProgram(Program program) throws DatabaseOperationException;

    Program updateProgram(Program program) throws DatabaseOperationException;

    List<Program> getProgramsForAgency(String agencyId);

}