package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.ArrayList;
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
    private String questionString;
    private Double weight;

    private int numberOfAnswers;

    @ManyToMany
    private List<Questionnaire> questionnaires;

    @Transient
    private Integer answer;


    /**
     * Create a new empty question.
     */
    public Question()
    {

    }


    /**
     * Create a new question.
     *
     * @param questionString   Question.
     * @param weight           Weight of question in score calculation.
     * @param questionnaires   Questionnaires this question belongs on.
     */
    public Question(String questionString, Double weight, List<Questionnaire> questionnaires, int numberOfAnswers)
    {
        this.questionString = questionString;
        this.weight = weight;
        this.questionnaires = questionnaires;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionString()
    {
        return questionString;
    }


    public void setQuestionString(String questionString)
    {
        this.questionString = questionString;
    }


    public Double getWeight()
    {
        return weight;
    }


    public void setWeight(Double weight)
    {
        this.weight = weight;
    }


    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public void setQuestionnaires(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

    public void addQuestionnaire(Questionnaire questionnaire)
    {
        this.questionnaires.add(questionnaire);
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = Integer.parseInt(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionString='" + questionString + '\'' +
                ", weight=" + weight +
                ", numberOfAnswers=" + numberOfAnswers +
                ", questionnaires=" + questionnaires.toString() +
                ", answer=" + answer +
                '}';
    }
}
