package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;

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

    private QuestionAnswerGroupDTO questionAnswerGroup;
    private List<Long> questionnaireIDs;

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
     * @param questionString      Question.
     * @param weight              Weight of question in score calculation.
     * @param questionnaireIDs    Questionnaires this question belongs on.
     * @param questionAnswerGroup The answer group for this Question
     */
    public QuestionDTO(String questionString, Double weight, List<Long> questionnaireIDs, QuestionAnswerGroupDTO questionAnswerGroup)
    {
        this.questionString = questionString;
        this.weight = weight;
        this.questionnaireIDs = questionnaireIDs;
        this.questionAnswerGroup = questionAnswerGroup;
    }

    /**
     * Create a new QuestionDTO from Question.
     *
     * @param question Question to convert to QuestionDTO.
     */
    public QuestionDTO (Question question)
    {
        this.id = question.getId();
        this.questionString = question.getQuestionString();
        this.weight = question.getWeight();
        this.questionnaireIDs = question.getQuestionnaires().stream().map(Questionnaire::getId).toList();
        this.questionAnswerGroup = new QuestionAnswerGroupDTO(question.getQuestionAnswerGroup());
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

    public void setQuestionAnswerGroupDTO(QuestionAnswerGroupDTO questionAnswerGroup)
    {
        this.questionAnswerGroup = questionAnswerGroup;
    }

    public QuestionAnswerGroupDTO getQuestionAnswerGroup() {
        return questionAnswerGroup;
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
                ", questionAnswerGroup=" + questionAnswerGroup +
                ", questionnaires=" + questionnaireIDs +
                ", answer=" + answer +
                '}';
    }
}
