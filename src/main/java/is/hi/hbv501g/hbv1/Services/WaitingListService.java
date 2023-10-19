package is.hi.hbv501g.hbv1.Services;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;

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
     * @param waitingLR WaitingListRequest to save.
     * @return          Saved WaitingListRequest.
     */
    WaitingListRequest createNewRequest(WaitingListRequest waitingLR);


    /**
     * Deletes a WaitingListRequest with a corresponding id.
     *
     * @param waitingListID ID of the WaitingListRequest to delete.
     */
    void deleteRequest(Long waitingListID);


    /**
     * Updates a corresponding WaitingListRequest.
     *
     * @param waitingListID   ID of the request to update.
     * @param staff           Updated staff info, if any.
     * @param bodyPart        Updated body part info, if any.
     * @param description     Updated description, if any.
     * @param status          Updated status, if any.
     * @param questionnaireID Updated Questionnaire ID, if any.
     * @param addAnswers      Add questionnaire answers, if any.
     * @param grade           Updated grade, if any.
     * @return                Updated WaitingListRequest.
     */
    WaitingListRequest updateRequest(Long waitingListID, Staff staff, String bodyPart, String description, boolean status, Integer questionnaireID, QuestionnaireForm addAnswers, Double grade);

    /**
     *
     * @param waitingListRequest Waitning list request to be updated
     */
    void updateRequest(WaitingListRequest waitingListRequest);


    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    List<WaitingListRequest> getRequests();


    /**
     * Gets a WaitingListRequest with matching unique ID.
     *
     * @param waitingListID Unique ID of the WaitingListRequest object to find.
     * @return              WaitingListRequest with a matching ID, if any.
     */
    WaitingListRequest getRequestByID(Long waitingListID);


    /**
     * Gets a WaitingListRequest with matching patient.
     *
     * @param patient Patient registered for the WaitingListRequest.
     * @return        WaitingListRequest with matching patient, if any.
     */
    WaitingListRequest getRequestByPatient(Patient patient);


    /**
     * Finds WaitingListRequest by staff (physiotherapist).
     *
     * @param staff Staff member assigned to the WaitingListRequest.
     * @return      List of WaitingListRequest with matching Staff member, if any.
     */
    List<WaitingListRequest> getRequestByPhysiotherapist(Staff staff);
}
