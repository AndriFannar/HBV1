package is.hi.hbv501g.hbv1.persistence.entities.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import is.hi.hbv501g.hbv1.converters.IntegerListConverter;
import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;
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
public class WaitingListRequestDTO
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
    private UserDTO patient;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserDTO staff;
    private String description;
    private boolean status;
    private LocalDate dateOfRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private Questionnaire questionnaire;
    @Column(columnDefinition = "int[]")
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> questionnaireAnswers;
    private double grade;


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
        this.patient = new UserDTO(request.getPatient());
        this.staff = new UserDTO(request.getStaff());
        this.description = request.getDescription();
        this.status = request.isStatus();
        this.dateOfRequest = request.getDateOfRequest();
        this.questionnaire = request.getQuestionnaire();
        this.questionnaireAnswers = request.getQuestionnaireAnswers();
        this.grade = request.getGrade();
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
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateOfRequest=" + dateOfRequest +
                ", questionnaireID=" + questionnaire.getId() +
                ", questionnaire=" + questionnaireAnswers +
                ", grade=" + grade +
                '}';
    }
}
