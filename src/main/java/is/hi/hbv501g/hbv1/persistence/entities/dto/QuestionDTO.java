package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import jakarta.persistence.*;

import java.util.List;


/**
 * Health questions.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
public class QuestionDTO
{
    private Long id;


    // Variables.
    private String questionString;
    private Double weight;

    private int numberOfAnswers;
    private List<Long> questionnaireIDs;

    @Transient
    private Integer answer;


    /**
     * Create a new empty question.
     */
    public QuestionDTO()
    {
    }


    /**
     * Create a new question.
     *
     * @param questionString Question.
     * @param weight         Weight of question in score calculation.
     * @param questionnaireIDs Questionnaires this question belongs on.
     */
    public QuestionDTO(String questionString, Double weight, List<Long> questionnaireIDs, int numberOfAnswers)
    {
        this.questionString = questionString;
        this.weight = weight;
        this.questionnaireIDs = questionnaireIDs;
        this.numberOfAnswers = numberOfAnswers;
    }

    public QuestionDTO (Question question)
    {
        this.id = question.getId();
        this.questionString = question.getQuestionString();
        this.weight = question.getWeight();
        this.questionnaireIDs = question.getQuestionnaires().stream().map(Questionnaire::getId).toList();
        this.numberOfAnswers = question.getNumberOfAnswers();
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

    public List<Long> getQuestionnaireIDs() {
        return questionnaireIDs;
    }

    public void setQuestionnaireIDs(List<Long> questionnaireIDs) {
        this.questionnaireIDs = questionnaireIDs;
    }

    public void addQuestionnaire(Questionnaire questionnaire)
    {
        this.questionnaireIDs.add(questionnaire.getId());
    }

    public void setNumberOfAnswers(int numberOfAnswers)
    {
        this.numberOfAnswers = numberOfAnswers;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer)
    {
        this.answer = answer;
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
                ", questionnaires=" + questionnaireIDs +
                ", answer=" + answer +
                '}';
    }
}
