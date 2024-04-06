package is.hi.hbv501g.hbv1.persistence.entities;

import is.hi.hbv501g.hbv1.converters.StringListConverter;
import jakarta.persistence.*;

import java.util.List;

/**
 * Health question answer groups.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
@Entity
@Table(name="questionAnswerGroup")
public class QuestionAnswerGroup
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

    private String groupName;

    // Variables.
    @Column(columnDefinition = "text[]")
    @Convert(converter = StringListConverter.class)
    private List<String> questionAnswers;

    @OneToMany(mappedBy = "questionAnswerGroup" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;


    /**
     * Create a new empty question.
     */
    public QuestionAnswerGroup()
    {
    }

    /**
     * Create a new QuestionAnswerGroup.
     *
     * @param groupName       Name of QuestionAnswerGroup.
     * @param questionAnswers Answers of Questions.
     */
    public QuestionAnswerGroup(String groupName, List<String> questionAnswers)
    {
        this.groupName = groupName;
        this.questionAnswers = questionAnswers;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuestionAnswerGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", questionAnswers=" + questionAnswers +
                ", questions=" + questions.size() +
                '}';
    }
}
