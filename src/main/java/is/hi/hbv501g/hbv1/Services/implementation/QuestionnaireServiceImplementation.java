package is.hi.hbv501g.hbv1.services.implementation;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionDTO;
import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionnaireDTO;
import is.hi.hbv501g.hbv1.persistence.repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.persistence.repositories.QuestionnaireRepository;
import is.hi.hbv501g.hbv1.services.QuestionnaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class implementation for Questionnaire objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
@Service
public class QuestionnaireServiceImplementation implements QuestionnaireService
{
    // Variables.
    final QuestionnaireRepository questionnaireRepository;
    final QuestionRepository questionRepository;


    /**
     * Constructs a new QuestionnaireServiceImplementation.
     *
     * @param questionnaireRepository QuestionnaireRepository linked to service.
     * @param questionRepository      QuestionRepository linked to service.
     */
    @Autowired
    public QuestionnaireServiceImplementation(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository)
    {
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
    }


    /**
     * Saves a new Questionnaire to the database.
     *
     * @param questionnaire New Questionnaire to save.
     * @return              Saved Questionnaire.
     */
    public QuestionnaireDTO saveNewQuestionnaire(QuestionnaireDTO questionnaire)
    {
        Questionnaire newQuestionnaire = new Questionnaire();
        newQuestionnaire.setName(questionnaire.getName());
        newQuestionnaire.setDisplayOnForm(questionnaire.isDisplayOnForm());
        newQuestionnaire.setQuestions(new ArrayList<>(questionRepository.findAllById(questionnaire.getQuestions().stream().map(QuestionDTO::getId).toList())));

        questionnaireRepository.save(newQuestionnaire);

        return questionnaire;
    }


    /**
     * Gets all Questionnaire objects in database.
     *
     * @return List of all Questionnaire objects in database, if any.
     */
    @Override
    public List<Questionnaire> getAllQuestionnaires()
    {
        return questionnaireRepository.getAllByOrderByNameAsc();
    }


    /**
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionnaireID The ID of the questionnaire to fetch.
     * @return                Questionnaire that holds Question objects with corresponding list ID.
     */
    @Override
    public Questionnaire getQuestionnaireByID(Long questionnaireID)
    {
        return questionnaireRepository.getById(questionnaireID);
    }


    /**
     * Gets the Questionnaires that should display when creating a new WaitingListRequest.
     *
     * @return List of Questionnaires that are marked for display.
     */
    @Override
    public List<Questionnaire> getQuestionnairesOnForm()
    {
        return questionnaireRepository.getByDisplayOnForm(true);
    }


    /**
     * Adds a Question to Questionnaire.
     *
     * @param questionID      ID of Question to add.
     * @param questionnaireID ID of Questionnaire that Question should be added to.
     */
    @Override
    @Transactional
    public void addQuestionToQuestionnaire(Long questionID, Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireID);
        Question question = questionRepository.getQuestionById(questionID);

        if ((questionnaire != null) && (question != null)) questionnaire.addQuestion(question);
    }


    /**
     * Removes a Question from questionnaire.
     *
     * @param questionID      ID of Question to remove.
     * @param questionnaireID ID of Questionnaire that Question should be removed from.
     */
    @Override
    @Transactional
    public void removeQuestionFromQuestionnaire(Long questionID, Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireID);
        Question question = questionRepository.getQuestionById(questionID);

        if ((questionnaire != null) && (question != null))
        {
            questionnaire.removeQuestion(question);
        }
    }


    /**
     * Toggles whether to display Questionnaire on registration page or not.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     */
    @Override
    @Transactional
    public void toggleDisplayQuestionnaireOnForm(Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireID);

        if(questionnaire != null)
        {
            questionnaire.setDisplayOnForm(!questionnaire.isDisplayOnForm());
        }
    }


    /**
     * Deletes a Questionnaire with a corresponding id.
     *
     * @param questionnaireID ID of the Questionnaire to delete.
     */
    @Override
    @Transactional
    public void deleteQuestionnaireByID(Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireID);

        questionnaire.setQuestions(new ArrayList<>());

        questionnaireRepository.deleteById(questionnaireID);
    }
}
