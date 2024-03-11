package is.hi.hbv501g.hbv1.persistence.entities;

import is.hi.hbv501g.hbv1.converters.IntegerListConverter;
import is.hi.hbv501g.hbv1.persistence.entities.dto.WaitingListRequestDTO;
import jakarta.persistence.*;

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

        this.questionnaireAnswers = new ArrayList<>();
    }


    /**
     * Create a WaitingListRequest.
     *
     * @param patient     The patient requesting treatment.
     * @param staff       Selected physiotherapist.
     * @param description Description of ailment.
     */
    public WaitingListRequest(User patient, User staff, String description) {
        this.patient = patient;
        this.staff = staff;
        this.description = description;

        this.status = false;
        this.grade = 0;

        this.dateOfRequest = LocalDate.now();

        this.questionnaireAnswers = new ArrayList<>();
    }

    /**
     * Create a new WaitingListRequest from WaitingListRequestDTO.
     *
     * @param request WaitingListRequestDTO to create WaitingListRequest from.
     */
    public WaitingListRequest(WaitingListRequestDTO request)
    {
        if (request.getId() != null) this.id = request.getId();
        this.description = request.getDescription();
        this.status = request.isStatus();
        this.dateOfRequest = request.getDateOfRequest();
        this.questionnaireAnswers = request.getQuestionnaireAnswers();
        this.grade = request.getGrade();
    }


    /**
     * Calculates the waiting list score according to given answers.
     */
    public void calculateScore(List<Question> questions)
    {
        for (int i = 0; i < questions.size(); i++)
        {
            grade += questionnaireAnswers.get(i) * questions.get(i).getWeight();
        }
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
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaire.getId() +
                ", questionnaire=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
