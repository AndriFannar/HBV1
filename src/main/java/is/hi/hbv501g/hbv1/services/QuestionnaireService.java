package is.hi.hbv501g.hbv1.services;

import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionnaireDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service class for health Questionnaires.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
public interface QuestionnaireService
{
    /**
     * Saves a new Questionnaire to the database.
     *
     * @param questionnaire New Questionnaire to save.
     * @return              Saved Questionnaire.
     */
    QuestionnaireDTO saveNewQuestionnaire(QuestionnaireDTO questionnaire);


    /**
     * Gets all Questionnaire objects in database.
     *
     * @return List of all Questionnaire objects in database, if any.
     */
    List<Questionnaire> getAllQuestionnaires();


    /**
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionnaireID The ID of the questionnaire to fetch.
     * @return                Questionnaire that holds Question objects with corresponding list ID.
     */
    Questionnaire getQuestionnaireByID(Long questionnaireID);


    /**
     * Gets the Questionnaires that should display when creating a new WaitingListRequest.
     *
     * @return List of Questionnaires that are marked for display.
     */
    List<Questionnaire> getQuestionnairesOnForm();


    /**
     * Updates Questionnaire with information from an updated Questionnaire object.
     *
     * @param updatedQuestionnaire Questionnaire with updated information.
     */
    void updateQuestionnaire(QuestionnaireDTO updatedQuestionnaire);


    /**
     * Toggles whether to display Questionnaire on registration page or not.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     * @param updatedDisplay  Updated display status of Questionnaire.
     */
    void setDisplayQuestionnaireOnForm(Long questionnaireID, boolean updatedDisplay);


    /**
     * Deletes a Questionnaire with a corresponding id.
     *
     * @param questionnaireID ID of the Questionnaire to delete.
     */
    void deleteQuestionnaireByID(Long questionnaireID);
}
