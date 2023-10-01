package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    @OneToOne(fetch = FetchType.LAZY)
    private Staff staff;
    private String bodyPart;
    private String description;
    private boolean status;
    private LocalDateTime dateOfRequest;
    private int questionnaireID;
    private int[] questionnaireAnswers;
    private double grade;

    /**
     * Create an empty WaitingListRequest.
     */
    public WaitingListRequest()
    {

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

        System.out.println(LocalDateTime.now());

        this.dateOfRequest = LocalDateTime.now();
    }

    /**
     * Calculates the waiting list score according to given answers.
     *
     * @return Calculated score
     */
    public double calculateScore(List<Question> questions)
    {
        for (int i = 0; i < questions.size(); i++) {
            grade += questionnaireAnswers[i] * questions.get(i).getWeight();
        }

        return grade;
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

    public int[] getQuestionnaireAnswers() {
        return questionnaireAnswers;
    }

    public void setQuestionnaireAnswers(int[] answers) {
        this.questionnaireAnswers = answers;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "WaitingListRequest{" +
                "id=" + id +
                ", patient=" + patient +
                ", staff=" + staff +
                ", bodyPart='" + bodyPart + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaireID +
                ", questionnaire=" + Arrays.toString(questionnaireAnswers) +
                ", grade=" + grade +
                '}';
    }
}
