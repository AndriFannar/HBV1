package is.hi.hbv501g.hbv1.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient health questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Entity
@Table(name="questionnaire")
public class Questionnaire
{
    @Id
    @SequenceGenerator(
            name="questionnaire_sequence",
            sequenceName = "questionnaire_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "questionnaire_sequence"
    )
    private Long id;


    // Variables
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Question> questions;

    private boolean displayOnForm;

    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<WaitingListRequest> waitingListRequests;


    /**
     * Create a new empty questionnaire.
     */
    public Questionnaire()
    {
        this.questions = new ArrayList<>();
        this.displayOnForm = false;
    }

    public Questionnaire(String name, List<Question> questions, boolean displayOnForm)
    {
        this.name = name;
        this.questions = questions;
        this.displayOnForm = displayOnForm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question)
    {
        this.questions.add(question);
    }

    public void removeQuestion(Question question)
    {
        this.questions.remove(question);
    }

    public boolean isDisplayOnForm() {
        return displayOnForm;
    }

    public void setDisplayOnForm(boolean displayOnForm) {
        this.displayOnForm = displayOnForm;
    }

    public List<WaitingListRequest> getWaitingListRequests() {
        return waitingListRequests;
    }

    public void setWaitingListRequests(List<WaitingListRequest> waitingListRequests) {
        this.waitingListRequests = waitingListRequests;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questions=" + questions.size() +
                '}';
    }
}
