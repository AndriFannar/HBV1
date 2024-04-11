package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;

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
    private List<Long> waitingListRequestIDs;


    /**
     * Create a new empty questionnaire.
     */
    public QuestionnaireDTO()
    {
        this.questions = new ArrayList<>();
        this.waitingListRequestIDs = new ArrayList<>();
        this.displayOnForm = false;
    }

    public QuestionnaireDTO(Questionnaire questionnaire)
    {
        this.id = questionnaire.getId();
        this.name = questionnaire.getName();
        this.questions = questionnaire.getQuestions().stream().map(QuestionDTO::new).toList();
        this.displayOnForm = questionnaire.isDisplayOnForm();
        this.waitingListRequestIDs = questionnaire.getWaitingListRequests().stream().map(WaitingListRequest::getId).toList();
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

    public List<Long> getWaitingListRequestIDs() {
        return waitingListRequestIDs;
    }

    public void setWaitingListRequestIDs(List<Long> waitingListRequestIDs) {
        this.waitingListRequestIDs = waitingListRequestIDs;
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
