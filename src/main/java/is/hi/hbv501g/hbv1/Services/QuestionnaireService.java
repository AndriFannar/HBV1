package is.hi.hbv501g.hbv1.Services;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;

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
     * @param question Question to save.
     */
    void addQuestionToList(Question question, Questionnaire questionnaire);


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
}
