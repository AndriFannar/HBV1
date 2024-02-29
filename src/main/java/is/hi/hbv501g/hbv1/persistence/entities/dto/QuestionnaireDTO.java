package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient health questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
public class QuestionnaireDTO
{
    private Long id;

    // Variables
    private String name;
    private List<Long> questionIDs;
    private boolean displayOnForm;


    /**
     * Create a new empty questionnaire.
     */
    public QuestionnaireDTO()
    {
        this.questionIDs = new ArrayList<>();
        this.displayOnForm = false;
    }

    public QuestionnaireDTO(String name, List<Long> questionIDs, boolean displayOnForm)
    {
        this.name = name;
        this.questionIDs = questionIDs;
        this.displayOnForm = displayOnForm;
    }

    public QuestionnaireDTO(Questionnaire questionnaire)
    {
        this.id = questionnaire.getId();
        this.name = questionnaire.getName();
        this.questionIDs = questionnaire.getQuestions().stream().map(Question::getId).toList();
        this.displayOnForm = questionnaire.isDisplayOnForm();
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

    public List<Long> getQuestionIDs() {
        return questionIDs;
    }

    public void setQuestionIDs(List<Long> questionIDs) {
        this.questionIDs = questionIDs;
    }

    public void addQuestion(Question question)
    {
        this.questionIDs.add(question.getId());
    }

    public void removeQuestion(Question question)
    {
        this.questionIDs.remove(question.getId());
    }

    public boolean isDisplayOnForm() {
        return displayOnForm;
    }

    public void setDisplayOnForm(boolean displayOnForm) {
        this.displayOnForm = displayOnForm;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questions=" + questionIDs.size() +
                '}';
    }
}
