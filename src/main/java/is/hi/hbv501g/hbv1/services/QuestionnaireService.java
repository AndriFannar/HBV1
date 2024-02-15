package is.hi.hbv501g.hbv1.services;

import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;

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
    Questionnaire saveNewQuestionnaire(Questionnaire questionnaire);


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
     * Adds a Question to Questionnaire.
     *
     * @param questionID      ID of Question to add.
     * @param questionnaireID ID of Questionnaire that Question should be added to.
     */
    void addQuestionToQuestionnaire(Long questionID, Long questionnaireID);


    /**
     * Removes a Question from questionnaire.
     *
     * @param questionID      ID of Question to remove.
     * @param questionnaireID ID of Questionnaire that Question should be removed from.
     */
    void removeQuestionFromQuestionnaire(Long questionID, Long questionnaireID);


    /**
     * Toggles whether to display Questionnaire on registration page or not.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     */
    void toggleDisplayQuestionnaireOnForm(Long questionnaireID);


    /**
     * Deletes a Questionnaire with a corresponding id.
     *
     * @param questionnaireID ID of the Questionnaire to delete.
     */
    void deleteQuestionnaireByID(Long questionnaireID);
}
