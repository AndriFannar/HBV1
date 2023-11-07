package is.hi.hbv501g.hbv1.Persistence.Entities;

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

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    /**
     * Create a new empty questionnaire.
     */
    public Questionnaire()
    {
        questions = new ArrayList<>();
    }

    public Questionnaire(String name, List<Question> questions)
    {
        this.name = name;
        this.questions = questions;
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
}
