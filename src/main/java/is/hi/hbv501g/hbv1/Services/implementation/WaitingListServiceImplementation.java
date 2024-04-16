package is.hi.hbv501g.hbv1.services.implementation;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.User;
import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.persistence.entities.dto.WaitingListRequestDTO;
import is.hi.hbv501g.hbv1.persistence.repositories.WaitingListRepository;
import is.hi.hbv501g.hbv1.services.QuestionnaireService;
import is.hi.hbv501g.hbv1.services.UserService;
import is.hi.hbv501g.hbv1.services.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


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
    private final UserService userService;
    private final QuestionnaireService questionnaireService;


    /**
     * Constructs a new WaitingListServiceImplementation.
     *
     * @param waitingListRepository WaitingListRepository linked to service.
     * @param userService           UserService linked to service.
     * @param questionnaireService  QuestionnaireService linked to service.
     */
    @Autowired
    public WaitingListServiceImplementation(WaitingListRepository waitingListRepository, UserService userService, QuestionnaireService questionnaireService)
    {
        this.waitingListRepository = waitingListRepository;
        this.userService = userService;
        this.questionnaireService = questionnaireService;
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
        return waitingListRepository.getById(requestID);
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
        return waitingListRepository.getByStaffOrderByGradeDescPatientNameAsc(staff);
    }


    /**
     * Updates a corresponding WaitingListRequest.
     *
     * @param updatedRequest WaitingListRequest with updated info.
     */
    @Transactional
    public void updateWaitingListRequest(WaitingListRequestDTO updatedRequest)
    {
        WaitingListRequest waitingLR = waitingListRepository.getById(updatedRequest.getId());

        if (waitingLR != null)
        {
            if (updatedRequest.getStaff().getId() != null) waitingLR.setStaff(userService.getUserByID(updatedRequest.getStaff().getId()));
            if (updatedRequest.getDescription() != null) waitingLR.setDescription(updatedRequest.getDescription());
            if (updatedRequest.isStatus()) waitingLR.setStatus(true);
            if ((updatedRequest.getQuestionnaireAnswers() != null) && (!updatedRequest.getQuestionnaireAnswers().isEmpty())) waitingLR.setQuestionnaireAnswers(updatedRequest.getQuestionnaireAnswers());
            if ((updatedRequest.getQuestionnaire() != null) && (!Objects.equals(updatedRequest.getQuestionnaire().getId(), waitingLR.getQuestionnaire().getId())))
            {
                waitingLR.setQuestionnaire(questionnaireService.getQuestionnaireByID(updatedRequest.getQuestionnaire().getId()));
                waitingLR.clearQuestionnaireAnswers();
            }

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
    @Deprecated
    @Transactional
    public void updateQuestionnaireAnswers(Long requestID, Questionnaire questionnaire)
    {
        WaitingListRequest request = waitingListRepository.getById(requestID);

        if(request != null)
        {
            if(!request.getQuestionnaireAnswers().isEmpty())
            {
                request.setQuestionnaireAnswers(new HashMap<>());
            }

            for (Question question : questionnaire.getQuestions())
            {
                request.addQuestionnaireAnswer(question.getId(), question.getAnswer());
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
