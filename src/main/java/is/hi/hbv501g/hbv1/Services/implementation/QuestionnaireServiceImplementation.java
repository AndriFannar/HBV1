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
     * Updates Questionnaire with information from an updated Questionnaire object.
     *
     * @param updatedQuestionnaire Questionnaire with updated information.
     */
    @Override
    @Transactional
    public void updateQuestionnaire(QuestionnaireDTO updatedQuestionnaire)
    {
        Questionnaire questionnaire = getQuestionnaireByID(updatedQuestionnaire.getId());

        if (questionnaire != null)
        {
            questionnaire.setName(updatedQuestionnaire.getName());
            questionnaire.setDisplayOnForm(updatedQuestionnaire.isDisplayOnForm());
            questionnaire.setQuestions(updatedQuestionnaire.getQuestions().stream()
                    .map(questionDTO -> questionRepository.getQuestionById(questionDTO.getId()))
                    .toList());
        }
    }


    /**
     * Toggles whether to display Questionnaire on registration page or not.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     * @param updatedDisplay  Updated display status of Questionnaire.
     */
    @Override
    @Transactional
    public void setDisplayQuestionnaireOnForm(Long questionnaireID, boolean updatedDisplay)
    {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireID);

        if(questionnaire != null)
        {
            questionnaire.setDisplayOnForm(updatedDisplay);
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
