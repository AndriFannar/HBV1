package is.hi.hbv501g.hbv1.Services;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
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
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionnaireID The ID of the questionnaire to fetch.
     * @return                Questionnaire that holds Question objects with corresponding list ID.
     */
    Questionnaire getQuestionnaire(Long questionnaireID);


    /**
     * Saves a new Questionnaire to the database.
     *
     * @param questionnaire New Questionnaire to save.
     * @return              Saved Questionnaire.
     */
    Questionnaire saveQuestionnaire(Questionnaire questionnaire);


    /**
     * Saves a new Question to database.
     *
     * @param questionID      ID of Question to save.
     * @param questionnaireID ID of Questionnaire that Question should be added to.
     */
    void addQuestionToList(Long questionID, Long questionnaireID);


    /**
     * Removes a Question from questionnaire.
     *
     * @param questionID      ID of Question to remove.
     * @param questionnaireID ID of Questionnaire that Question should be removed from.
     */
    void removeQuestionFromList(Long questionID, Long questionnaireID);


    /**
     * Display Questionnaire on registration page.
     *
     * @param questionnaireID ID of the Questionnaire to change.
     */
    void displayOnForm(Long questionnaireID);


    /**
     * Deletes a Questionnaire with a corresponding id.
     *
     * @param questionnaireID ID of the Questionnaire to delete.
     */
    void deleteQuestionnaireById(Long questionnaireID);


    /**
     * Gets all Questionnaire objects in database.
     *
     * @return List of all Questionnaire objects in database, if any.
     */
    List<Questionnaire> getAllQuestionnaire();


    /**
     * Gets the Questionnaires that should display when creating a new WaitingListRequest.
     *
     * @return List of Questionnaires that are marked for display.
     */
    List<Questionnaire> getDisplayQuestionnaires();
}
