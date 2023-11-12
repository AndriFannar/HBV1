package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionnaireRepository;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

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
    QuestionnaireRepository questionnaireRepository;
    QuestionRepository questionRepository;


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
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionnaireID The ID of the questionnaire to fetch.
     * @return                Questionnaire that holds Question objects with corresponding list ID.
     */
    @Override
    public Questionnaire getQuestionnaire(Long questionnaireID)
    {
        return questionnaireRepository.getQuestionnaireById(questionnaireID);
    }


    /**
     * Saves a new Questionnaire to the database.
     *
     * @param questionnaire New Questionnaire to save.
     * @return              Saved Questionnaire.
     */
    public Questionnaire saveQuestionnaire(Questionnaire questionnaire)
    {
        return questionnaireRepository.save(questionnaire);
    }


    /**
     * Saves a new Question to database.
     *
     * @param questionID      ID of Question to save.
     * @param questionnaireID ID of Questionnaire that Question should be added to.
     */
    @Override
    @Transactional
    public void addQuestionToList(Long questionID, Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireID);
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
    public void removeQuestionFromList(Long questionID, Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireID);
        Question question = questionRepository.getQuestionById(questionID);

        if ((questionnaire != null) && (question != null))
        {
            questionnaire.removeQuestion(question);
        }
    }


    /**
     * Display Questionnaire on registration page.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     */
    @Override
    @Transactional
    public void displayOnForm(Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireID);

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
    public void deleteQuestionnaireById(Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(questionnaireID);

        questionnaire.setQuestions(new ArrayList<>());

        questionnaireRepository.deleteById(questionnaireID);
    }


    /**
     * Gets all Questionnaire objects in database.
     *
     * @return List of all Questionnaire objects in database, if any.
     */
    @Override
    public List<Questionnaire> getAllQuestionnaire()
    {
        return questionnaireRepository.findAllByOrderByNameAsc();
    }


    /**
     * Gets the Questionnaires that should display when creating a new WaitingListRequest.
     *
     * @return List of Questionnaires that are marked for display.
     */
    @Override
    public List<Questionnaire> getDisplayQuestionnaires()
    {
        return questionnaireRepository.getQuestionnaireByDisplayOnForm(true);
    }
}
