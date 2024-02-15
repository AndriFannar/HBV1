package is.hi.hbv501g.hbv1.services;

import is.hi.hbv501g.hbv1.persistence.entities.*;

import java.util.List;


/**
 * Service class for WaitingListRequest.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
public interface WaitingListService
{
    /**
     * Saves a new WaitingListRequest in database.
     *
     * @param request WaitingListRequest to save.
     * @return                   Saved WaitingListRequest.
     */
    WaitingListRequest saveNewWaitingListRequest(WaitingListRequest request);


    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    List<WaitingListRequest> getAllWaitingListRequests();


    /**
     * Gets a WaitingListRequest with matching unique ID.
     *
     * @param requestID Unique ID of the WaitingListRequest object to find.
     * @return          WaitingListRequest with a matching ID, if any.
     */
    WaitingListRequest getWaitingListRequestByID(Long requestID);


    /**
     * Gets a WaitingListRequest with matching patient.
     *
     * @param patient Patient registered for the WaitingListRequest.
     * @return        WaitingListRequest with matching patient, if any.
     */
    WaitingListRequest getWaitingListRequestByPatient(User patient);


    /**
     * Finds WaitingListRequest by physiotherapist.
     *
     * @param staff Staff member assigned to the WaitingListRequest.
     * @return      List of WaitingListRequest with matching Staff member, if any.
     */
    List<WaitingListRequest> getWaitingListRequestByPhysiotherapist(User staff);


    /**
     * Updates a corresponding WaitingListRequest.
     *
     * @param updatedRequest WaitingListRequest with updated info.
     */
    void updateWaitingListRequest(WaitingListRequest updatedRequest);


    /**
     * Change the status of a WaitingListRequest.
     *
     * @param requestID ID of the WaitingListRequest to change;
     */
    void updateWaitingListRequestStatus(Long requestID, boolean newStatus);


    /**
     * Adds answers from Questionnaire to WaitingListRequest.
     *
     * @param requestID     WaitingListRequest that the answers belong to.
     * @param questionnaire Questionnaire that has the answers.
     */
    void updateQuestionnaireAnswers(Long requestID, Questionnaire questionnaire);


    /**
     * Deletes a WaitingListRequest with a corresponding id.
     *
     * @param requestID ID of the WaitingListRequest to delete.
     */
    void deleteWaitingListRequestByID(Long requestID);
}
