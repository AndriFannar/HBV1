package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Waiting list request for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Entity
@Table(name="waitingListRequest")
public class WaitingListRequest
{
    // Database primary key.
    @Id
    @SequenceGenerator(
            name="waitingList_sequence",
            sequenceName = "waitingList_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "waitingList_sequence"
    )
    private Long id;

    // Variables.
    @OneToOne(fetch = FetchType.LAZY)
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    private Staff staff;
    private String bodyPart;
    private String description;
    private boolean status;
    private LocalDateTime dateOfRequest;
    private int questionnaireID;

    @ElementCollection
    private List<Integer> questionnaireAnswers;
    private double grade;


    /**
     * Create an empty WaitingListRequest.
     */
    public WaitingListRequest()
    {
        this.status = false;
        this.questionnaireID = 1;
        this.grade = 0;

        this.dateOfRequest = LocalDateTime.now();

        this.questionnaireAnswers = new ArrayList<>();
    }


    /**
     * Create a WaitingListRequest.
     *
     * @param patient     The patient requesting treatment.
     * @param staff       Selected physiotherapist.
     * @param bodyPart    Body part needing care.
     * @param description Description of ailment.
     */
    public WaitingListRequest(Patient patient, Staff staff, String bodyPart, String description) {
        this.patient = patient;
        this.staff = staff;
        this.bodyPart = bodyPart;
        this.description = description;

        this.status = false;
        this.questionnaireID = 0;
        this.grade = 0;

        this.dateOfRequest = LocalDateTime.now();

        this.questionnaireAnswers = new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

    public LocalDateTime getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(LocalDateTime dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public int getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(int questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public List<Integer> getQuestionnaireAnswers() {
        return questionnaireAnswers;
    }

    public void setQuestionnaireAnswers(List<Integer> answers) {
        this.questionnaireAnswers = answers;
    }

    public void addQuestionnaireAnswer(Question question)
    {
        this.questionnaireAnswers.add(question.getAnswer());
        this.grade += question.getAnswer() * question.getWeight();
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
                ", patient=" + patient.getId() +
                ", staff=" + staff +
                ", bodyPart='" + bodyPart + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaireID +
                ", questionnaire=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
