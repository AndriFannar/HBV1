package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Waiting list request for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
public class WaitingListRequestDTO
{
    // Database primary key.
    private Long id;


    // Variables.
    private UserDTO patient;
    private UserDTO staff;
    private String description;
    private boolean status;
    private LocalDate dateOfRequest;
    private QuestionnaireDTO questionnaire;
    private Map<Long, Integer> questionnaireAnswers;
    private double grade;


    /**
     * Constructs a new WaitingListRequestDTO.
     *
     * @param id                   Unique ID of the WaitingListRequest.
     * @param patient              Patient registered for the WaitingListRequest.
     * @param staff                Staff member assigned to the WaitingListRequest.
     * @param description          Description of the WaitingListRequest.
     * @param status               Status of the WaitingListRequest.
     * @param dateOfRequest        Date of the WaitingListRequest.
     * @param questionnaire        Questionnaire for the WaitingListRequest.
     * @param questionnaireAnswers Answers to the questionnaire.
     * @param grade                Grade of the WaitingListRequest.
     */
    public WaitingListRequestDTO(Long id, UserDTO patient, UserDTO staff, String description, boolean status, LocalDate dateOfRequest, QuestionnaireDTO questionnaire, Map<Long, Integer> questionnaireAnswers, double grade) {
        this.id = id;
        this.patient = patient;
        this.staff = staff;
        this.description = description;
        this.status = status;
        this.dateOfRequest = dateOfRequest;
        this.questionnaire = questionnaire;
        this.questionnaireAnswers = questionnaireAnswers;
        this.grade = grade;
    }

    /**
     * Create an empty WaitingListRequest.
     */
    public WaitingListRequestDTO()
    {
        this.status = false;
        this.grade = 0;

        this.dateOfRequest = LocalDate.now();

        this.questionnaireAnswers = new HashMap<>();
    }


    /**
     * Create a WaitingListRequestDTO from a WaitingListRequest.
     *
     * @param request WaitingListRequest to create WaitingListRequestDTO from.
     */
    public WaitingListRequestDTO(WaitingListRequest request)
    {
        this.id = request.getId();
        this.patient = new UserDTO(request.getPatient());
        this.staff = new UserDTO(request.getStaff());
        this.description = request.getDescription();
        this.status = request.isStatus();
        this.dateOfRequest = request.getDateOfRequest();
        this.questionnaire = new QuestionnaireDTO(request.getQuestionnaire());
        this.questionnaireAnswers = request.getQuestionnaireAnswers();
        this.grade = request.getGrade();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getPatient() {
        return patient;
    }

    public void setPatient(UserDTO patient) {
        this.patient = patient;
    }

    public UserDTO getStaff() {
        return staff;
    }

    public void setStaff(UserDTO staff) {
        this.staff = staff;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public QuestionnaireDTO getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireDTO questionnaire)
    {
        this.questionnaire = questionnaire;
    }

    public Map<Long, Integer> getQuestionnaireAnswers() {
        return questionnaireAnswers;
    }

    public void setQuestionnaireAnswers(Map<Long, Integer> questionnaireAnswers)
    {
        this.questionnaireAnswers = questionnaireAnswers;
    }

    public void addQuestionnaireAnswer(Long questionID, Integer answer)
    {
        this.questionnaireAnswers.put(questionID, answer);

    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString()
    {
        return "WaitingListRequest{" +
                "id=" + id +
                ", patient=" + patient +
                ", staff=" + staff +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaire=" + questionnaire +
                ", questionnaireAnswers=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
