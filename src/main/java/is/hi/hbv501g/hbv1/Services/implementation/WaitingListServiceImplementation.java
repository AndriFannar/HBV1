package is.hi.hbv501g.hbv1.Services.Implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Persistence.Repositories.WaitingListRepository;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service class implementation for WaitingListRequest.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Service
public class WaitingListServiceImplementation implements WaitingListService
{
    // Variables
    private final WaitingListRepository waitingListRepository;


    /**
     * Constructs a new WaitingListServiceImplementation.
     *
     * @param waitingListRepository WaitingListRepository linked to service.
     */
    @Autowired
    public WaitingListServiceImplementation(WaitingListRepository waitingListRepository)
    {
        this.waitingListRepository = waitingListRepository;
    }


    /**
     * Saves a new WaitingListRequest in database.
     *
     * @param waitingLR WaitingListRequest to save.
     * @return          Saved WaitingListRequest.
     */
    @Override
    public WaitingListRequest createNewRequest(WaitingListRequest waitingLR)
    {
        return waitingListRepository.save(waitingLR);
    }


    /**
     * Deletes a WaitingListRequest with a corresponding id.
     *
     * @param waitingListID ID of the WaitingListRequest to delete.
     */
    @Override
    public void deleteRequest(Long waitingListID)
    {
        waitingListRepository.deleteById(waitingListID);
    }


    /**
     * Change the status of a WaitingListRequest.
     *
     * @param waitingListID ID of the WaitingListRequest to change;
     */
    @Transactional
    public void updateRequestStatus(Long waitingListID, boolean newStatus)
    {
        WaitingListRequest request = waitingListRepository.getWaitingListRequestById(waitingListID);

        if(request != null)
        {
            request.setStatus(newStatus);
        }
    }


    /**
     * Updates a corresponding WaitingListRequest.
     *
     * @param waitingListID  ID of the request to update.
     * @param updatedRequest WaitingListRequest with updated info.
     */
    @Transactional
    public void updateRequest(Long waitingListID, WaitingListRequest updatedRequest)
    {
        WaitingListRequest waitingLR = waitingListRepository.getWaitingListRequestById(waitingListID);

        if (waitingLR != null)
        {
            if (updatedRequest.getStaff() != null) waitingLR.setStaff(updatedRequest.getStaff());
            if (updatedRequest.getBodyPart() != null) waitingLR.setBodyPart(updatedRequest.getBodyPart());
            if (updatedRequest.getDescription() != null) waitingLR.setDescription(updatedRequest.getDescription());
            if (updatedRequest.isStatus()) waitingLR.setStatus(true);
            if (updatedRequest.getQuestionnaireID() != 0) waitingLR.setQuestionnaireID(updatedRequest.getQuestionnaireID());
            if (updatedRequest.getGrade() != 0) waitingLR.setGrade(updatedRequest.getGrade());
        }
    }


    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    @Override
    public List<WaitingListRequest> getRequests()
    {
        return waitingListRepository.findAllByOrderByGradeDescPatientNameAsc();
    }


    /**
     * Gets a WaitingListRequest with matching unique ID.
     *
     * @param waitingListID Unique ID of the WaitingListRequest object to find.
     * @return              WaitingListRequest with a matching ID, if any.
     */
    @Override
    public WaitingListRequest getRequestByID(Long waitingListID)
    {
        return waitingListRepository.getWaitingListRequestById(waitingListID);
    }


    /**
     * Gets a WaitingListRequest with matching patient.
     *
     * @param patient Patient registered for the WaitingListRequest.
     * @return        WaitingListRequest with matching patient, if any.
     */
    @Override
    public WaitingListRequest getRequestByPatient(User patient)
    {
        return waitingListRepository.getWaitingListRequestByPatient(patient);
    }

    
    /**
     * Finds WaitingListRequest by staff (physiotherapist).
     *
     * @param staff Staff member assigned to the WaitingListRequest.
     * @return      List of WaitingListRequest with matching Staff member, if any.
     */
    @Override
    public List<WaitingListRequest> getRequestByPhysiotherapist(User staff)
    {
        return waitingListRepository.getWaitingListRequestByStaff(staff);
    }
}
