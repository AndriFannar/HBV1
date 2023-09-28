package is.hi.hbv501g.hbv1.Servecies;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;

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
     * @param id ID of the WaitingListRequest to delete.
     */
    void deleteRequest(Long id);

    /**
     * Updates a matching WaitingListRequest.
     *
     * @param waitingListID ID of WaitingListRequest to update.
     */
    void updateRequest(Long waitingListID, Staff staff, String bodyPart, String description, boolean status, int questionnaireID);

    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    List<WaitingListRequest> getRequests();

    /**
     * Gets a WaitingListRequest with matching unique ID.
     *
     * @param id Unique ID of the WaitingListRequest object to find.
     * @return   WaitingListRequest with a matching ID, if any.
     */
    WaitingListRequest getRequestByID(Long id);

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
