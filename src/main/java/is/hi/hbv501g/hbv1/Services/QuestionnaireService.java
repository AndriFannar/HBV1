package is.hi.hbv501g.hbv1.Services;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.QuestionnaireForm;

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
     * @param listID The ID of the questionnaire to fetch.
     * @return       Questionnaire that holds Question objects with corresponding list ID.
     */
    QuestionnaireForm getQuestionnaire(Integer listID);


    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    Question addQuestion(Question question);


    /**
     * Update a matching Question.
     *
     * @param questionID      ID of the question to update.
     * @param questionString  Updated question, if any.
     * @param weight          Updated weight, if any.
     * @param numberOfAnswers Updated number of answers possible, if any.
     * @param listID          Updated Questionnaire ID, if any.
     */
    void updateQuestion(Long questionID, String questionString, double weight, int numberOfAnswers, List<Integer> listID);


    /**
     * Deletes a Question with a corresponding id.
     *
     * @param questionID ID of the Question to delete.
     */
    void deleteQuestionById(Long questionID);


    /**
     * Gets all Question objects in database.
     *
     * @return List of all Question objects in database, if any.
     */
    List<Question> getQuestions();


    /**
     * Gets Question object with matching ID from database.
     *
     * @return Question object with matching ID in database, if any.
     */
    Question getQuestionById(Long questionID);
}
