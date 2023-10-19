package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;
import is.hi.hbv501g.hbv1.Persistence.Repositories.WaitingListRepository;
import is.hi.hbv501g.hbv1.Services.WaitingListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private WaitingListRepository waitingListRepository;


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
     * Updates a corresponding WaitingListRequest.
     *
     * @param waitingListID   ID of the request to update.
     * @param staff           Updated staff info, if any.
     * @param bodyPart        Updated body part info, if any.
     * @param description     Updated description, if any.
     * @param status          Update status (false -> true), (true -> false).
     * @param questionnaireID Updated Questionnaire ID, if any.
     * @param addAnswers      Add questionnaire answers, if any.
     * @param grade           Updated grade, if any.
     * @return                Updated WaitingListRequest.
     */
    @Override
    @Transactional
    public WaitingListRequest updateRequest(Long waitingListID, Staff staff, String bodyPart, String description, boolean status, Integer questionnaireID, QuestionnaireForm addAnswers, Double grade)
    {
        WaitingListRequest waitingLR = waitingListRepository.getWaitingListRequestById(waitingListID);
        if (waitingLR != null)
        {
            if (staff != null && !Objects.equals(waitingLR.getStaff(), staff)) waitingLR.setStaff(staff);
            if (bodyPart != null && !Objects.equals(waitingLR.getBodyPart(), bodyPart)) waitingLR.setBodyPart(bodyPart);
            if (description != null && !Objects.equals(waitingLR.getDescription(), description)) waitingLR.setDescription(description);
            if (status) waitingLR.setStatus(!waitingLR.isStatus());
            if (questionnaireID != null && !Objects.equals(waitingLR.getQuestionnaireID(), questionnaireID)) waitingLR.setQuestionnaireID(questionnaireID);
            if (grade != null && !Objects.equals(waitingLR.getGrade(), grade)) waitingLR.setGrade(grade);

            if (addAnswers != null)
            {
                for(Question question : addAnswers.getQuestions())
                {
                    System.out.println(question);
                    waitingLR.addQuestionnaireAnswer(question);
                    System.out.println(waitingLR.getGrade());
                }
            }
        }

        return waitingLR;
    }

    /**
     *
     * @param waitingListRequest Waitning list request to be updated
     */
    @Override
    public void updateRequest(WaitingListRequest waitingListRequest) {
        waitingListRepository.save(waitingListRequest);


    }


    /**
     * Gets all WaitingListRequest objects.
     *
     * @return List of all WaitingListRequest objects, if any.
     */
    @Override
    public List<WaitingListRequest> getRequests()
    {
        return waitingListRepository.findAllByOrderByGradeAsc();
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
    public WaitingListRequest getRequestByPatient(Patient patient)
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
    public List<WaitingListRequest> getRequestByPhysiotherapist(Staff staff)
    {
        return waitingListRepository.getWaitingListRequestByStaff(staff);
    }
}
