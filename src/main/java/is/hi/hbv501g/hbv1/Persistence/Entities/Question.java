package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Health questions.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Entity
@Table(name="question")
public class Question
{
    // Database primary key.
    @Id
    @SequenceGenerator(
            name="question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private Long id;

    // Variables.
    private String question;
    private Double weight;
    private int[] listID;

    /**
     * Create a new empty question.
     */
    public Question()
    {

    }

    /**
     * Create a new question.
     *
     * @param question Question.
     * @param weight   Weight of question in score calculation.
     * @param listID   Questionnaires this question belongs on.
     */
    public Question(String question, Double weight, int[] listID) {
        this.question = question;
        this.weight = weight;
        this.listID = listID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int[] getListID() {
        return listID;
    }

    public void setListID(int[] listID) {
        this.listID = listID;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", weight=" + weight +
                ", listID=" + listID +
                '}';
    }
}
