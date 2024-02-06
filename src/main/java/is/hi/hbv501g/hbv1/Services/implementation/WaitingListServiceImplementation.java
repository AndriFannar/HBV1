package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Persistence.Repositories.WaitingListRepository;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * @param request WaitingListRequest to save.
     * @return        Saved WaitingListRequest.
     */
    @Override
    public WaitingListRequest saveNewWaitingListRequest(WaitingListRequest request)
    {
        return waitingListRepository.save(request);
    }


    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    @Override
    public List<WaitingListRequest> getAllWaitingListRequests()
    {
        return waitingListRepository.getAllByOrderByGradeDescPatientNameAsc();
    }


    /**
     * Gets a WaitingListRequest with matching unique ID.
     *
     * @param requestID Unique ID of the WaitingListRequest object to find.
     * @return          WaitingListRequest with a matching ID, if any.
     */
    @Override
    public WaitingListRequest getWaitingListRequestByID(Long requestID)
    {
        WaitingListRequest request = waitingListRepository.getById(requestID);

        if (request != null)
        {
            List<Integer> answers = request.getQuestionnaireAnswers();

            for (int i = 0; i < request.getQuestionnaireAnswers().size(); i++)
            {
                request.getQuestionnaire().getQuestions().get(i).setAnswer(answers.get(i));
            }
        }

        return request;
    }


    /**
     * Gets a WaitingListRequest with matching patient.
     *
     * @param patient Patient registered for the WaitingListRequest.
     * @return        WaitingListRequest with matching patient, if any.
     */
    @Override
    public WaitingListRequest getWaitingListRequestByPatient(User patient)
    {
        return waitingListRepository.getByPatient(patient);
    }


    /**
     * Finds WaitingListRequest by physiotherapist.
     *
     * @param staff Staff member assigned to the WaitingListRequest.
     * @return      List of WaitingListRequest with matching Staff member, if any.
     */
    @Override
    public List<WaitingListRequest> getWaitingListRequestByPhysiotherapist(User staff)
    {
        return waitingListRepository.getByStaff(staff);
    }


    /**
     * Updates a corresponding WaitingListRequest.
     *
     * @param requestID  ID of the request to update.
     * @param updatedRequest WaitingListRequest with updated info.
     */
    @Transactional
    public void updateWaitingListRequest(Long requestID, WaitingListRequest updatedRequest)
    {
        WaitingListRequest waitingLR = waitingListRepository.getById(requestID);

        if (waitingLR != null)
        {
            if (updatedRequest.getStaff() != null) waitingLR.setStaff(updatedRequest.getStaff());
            if (updatedRequest.getDescription() != null) waitingLR.setDescription(updatedRequest.getDescription());
            if (updatedRequest.isStatus()) waitingLR.setStatus(true);
            if ((updatedRequest.getQuestionnaire() != null) && (updatedRequest.getQuestionnaire() != waitingLR.getQuestionnaire())) waitingLR.setQuestionnaire(updatedRequest.getQuestionnaire());
            if (updatedRequest.getGrade() != 0) waitingLR.setGrade(updatedRequest.getGrade());
        }
    }


    /**
     * Change the status of a WaitingListRequest.
     *
     * @param requestID ID of the WaitingListRequest to change;
     */
    @Transactional
    public void updateWaitingListRequestStatus(Long requestID, boolean newStatus)
    {
        WaitingListRequest request = waitingListRepository.getById(requestID);

        if(request != null)
        {
            request.setStatus(newStatus);
        }
    }


    /**
     * Adds answers from Questionnaire to WaitingListRequest.
     *
     * @param requestID     WaitingListRequest that the answers belong to.
     * @param questionnaire Questionnaire that has the answers.
     */
    @Transactional
    public void updateQuestionnaireAnswers(Long requestID, Questionnaire questionnaire)
    {
        WaitingListRequest request = waitingListRepository.getById(requestID);

        if(request != null)
        {
            if(!request.getQuestionnaireAnswers().isEmpty())
            {
                request.setQuestionnaireAnswers(new ArrayList<>());
            }

            for (Question question : questionnaire.getQuestions())
            {
                request.addQuestionnaireAnswer(question.getAnswer());
            }

            Questionnaire requestQuestionnaire = request.getQuestionnaire();

            request.calculateScore(requestQuestionnaire.getQuestions());
        }
    }


    /**
     * Deletes a WaitingListRequest with a corresponding id.
     *
     * @param requestID ID of the WaitingListRequest to delete.
     */
    @Override
    public void deleteWaitingListRequestByID(Long requestID)
    {
        waitingListRepository.deleteById(requestID);
    }
}
