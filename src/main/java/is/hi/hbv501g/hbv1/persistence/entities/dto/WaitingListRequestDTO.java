package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private Long patientID;
    private Long staffID;
    private String description;
    private boolean status;
    private LocalDate dateOfRequest;
    private Long questionnaireID;
    private List<Integer> questionnaireAnswers;
    private double grade;


    public WaitingListRequestDTO(Long id, Long patientID, Long staffID, String description, boolean status, LocalDate dateOfRequest, Long questionnaireID, List<Integer> questionnaireAnswers, double grade) {
        this.id = id;
        this.patientID = patientID;
        this.staffID = staffID;
        this.description = description;
        this.status = status;
        this.dateOfRequest = dateOfRequest;
        this.questionnaireID = questionnaireID;
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

        this.questionnaireAnswers = new ArrayList<>();
    }


    public WaitingListRequestDTO(WaitingListRequest request)
    {
        this.id = request.getId();
        this.patientID = request.getPatient().getId();
        this.staffID = request.getStaff().getId();
        this.description = request.getDescription();
        this.status = request.isStatus();
        this.dateOfRequest = request.getDateOfRequest();
        this.questionnaireID = request.getQuestionnaire().getId();
        this.questionnaireAnswers = request.getQuestionnaireAnswers();
        this.grade = request.getGrade();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
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

    public Long getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(Long questionnaireID)
    {
        this.questionnaireID = questionnaireID;
        this.questionnaireAnswers.clear();
    }

    public List<Integer> getQuestionnaireAnswers() {
        return questionnaireAnswers;
    }

    public void setQuestionnaireAnswers(List<Integer> questionnaireAnswers)
    {
        this.questionnaireAnswers = questionnaireAnswers;
    }

    public void addQuestionnaireAnswer(Integer answer)
    {
        this.questionnaireAnswers.add(answer);

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
                ", patient=" + patientID +
                ", staff=" + staffID +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaireID +
                ", questionnaire=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
