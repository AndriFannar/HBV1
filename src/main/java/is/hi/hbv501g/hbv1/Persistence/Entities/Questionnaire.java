package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Patient health questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Entity
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

    @OneToMany
    private List<Question> questions;
    private List<Integer> answers;

    /**
     * Create a new empty questionnaire.
     */
    public Questionnaire()
    {

    }

    public Questionnaire(List<Question> questions) {
        this.questions = questions;
    }

    public double calculateScore()
    {
        double score = 0;

        for (int i = 0; i < questions.size(); i++) {
            score += answers.get(i) * questions.get(i).getWeight();
        }

        return score;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }
}
