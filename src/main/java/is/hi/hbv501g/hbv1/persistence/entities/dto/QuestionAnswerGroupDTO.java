package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.QuestionAnswerGroup;

import java.util.List;

/**
 * Health question answer groups.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
public class QuestionAnswerGroupDTO
{
    // Database primary key.
    private Long id;

    // Variables.
    private String groupName;

    private List<String> questionAnswers;

    private List<Long> questionIDs;


    /**
     * Create a new empty QuestionAnswerGroupDTO.
     */
    public QuestionAnswerGroupDTO()
    {
    }

    /**
     * Create a new QuestionAnswerGroupDTO.
     *
     * @param groupName       Name of QuestionAnswerGroup
     * @param questionAnswers Answers of Questions.
     */
    public QuestionAnswerGroupDTO(String groupName, List<String> questionAnswers)
    {
        this.groupName = groupName;
        this.questionAnswers = questionAnswers;
    }

    /**
     * Create a new QuestionAnswerGroupDTO from QuestionAnswerGroup.
     *
     * @param questionAnswerGroup QuestionAnswerGroup to convert to QuestionAnswerGroupDTO.
     */
    public QuestionAnswerGroupDTO(QuestionAnswerGroup questionAnswerGroup)
    {
        this.id = questionAnswerGroup.getId();
        this.groupName = questionAnswerGroup.getGroupName();
        this.questionAnswers = questionAnswerGroup.getQuestionAnswers();
        this.questionIDs = questionAnswerGroup.getQuestions().stream().map(question -> getId()).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<String> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public List<Long> getQuestionIDs() {
        return questionIDs;
    }

    public void setQuestionIDs(List<Long> questionIDs) {
        this.questionIDs = questionIDs;
    }

    @Override
    public String toString() {
        return "QuestionAnswerGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", questionAnswers=" + questionAnswers +
                ", questionIDs=" + questionIDs +
                '}';
    }
}
