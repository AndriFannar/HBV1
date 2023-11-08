package is.hi.hbv501g.hbv1.Persistence.Entities;

import is.hi.hbv501g.hbv1.Converters.IntegerListConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;


/**
 * Waiting list request for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Entity
@Table(name="waiting_List_Request")
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
    private User patient;
    @ManyToOne(fetch = FetchType.LAZY)
    private User staff;
    private String bodyPart;
    private String description;
    private boolean status;
    private LocalDate dateOfRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaire;

    @Column(columnDefinition = "int[]")
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> questionnaireAnswers;
    private double grade;


    /**
     * Create an empty WaitingListRequest.
     */
    public WaitingListRequest()
    {
        this.status = false;
        this.grade = 0;

        this.dateOfRequest = LocalDate.now();
    }


    /**
     * Create a WaitingListRequest.
     *
     * @param patient     The patient requesting treatment.
     * @param staff       Selected physiotherapist.
     * @param bodyPart    Body part needing care.
     * @param description Description of ailment.
     */
    public WaitingListRequest(User patient, User staff, String bodyPart, String description) {
        this.patient = patient;
        this.staff = staff;
        this.bodyPart = bodyPart;
        this.description = description;

        this.status = false;
        this.grade = 0;

        this.dateOfRequest = LocalDate.now();
    }


    /**
     * Calculates the waiting list score according to given answers.
     *
     * @return Calculated score
     */
    public double calculateScore(List<Question> questions)
    {
        for (int i = 0; i < questions.size(); i++)
        {
            grade += questionnaireAnswers.get(i) * questions.get(i).getWeight();
        }

        return grade;
    }

    public Long getId() {
        return id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getStaff() {
        return staff;
    }

    public void setStaff(User staff) {
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

    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire)
    {
        this.questionnaire = questionnaire;
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
                ", patient=" + patient.getId() +
                ", staff=" + staff +
                ", bodyPart='" + bodyPart + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaire.getId() +
                ", questionnaire=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
