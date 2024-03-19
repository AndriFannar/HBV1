package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
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
    private List<QuestionDTO> questions;
    private boolean displayOnForm;


    /**
     * Create a new empty questionnaire.
     */
    public QuestionnaireDTO()
    {
        this.questions = new ArrayList<>();
        this.displayOnForm = false;
    }

    public QuestionnaireDTO(String name, List<QuestionDTO> questions, boolean displayOnForm)
    {
        this.name = name;
        this.questions = questions;
        this.displayOnForm = displayOnForm;
    }

    public QuestionnaireDTO(Questionnaire questionnaire)
    {
        this.id = questionnaire.getId();
        this.name = questionnaire.getName();
        this.questions = questionnaire.getQuestions().stream().map(QuestionDTO::new).toList();
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

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question)
    {
        this.questions.add(new QuestionDTO(question));
    }

    public void removeQuestion(Question question)
    {
        this.questions.remove(new QuestionDTO(question));
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
                ", questions=" + questions.size() +
                '}';
    }
}
