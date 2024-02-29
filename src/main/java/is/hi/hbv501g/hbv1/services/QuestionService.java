package is.hi.hbv501g.hbv1.services;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionDTO;

import java.util.Collection;
import java.util.List;


/**
 * Service class for health Questions.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
public interface QuestionService
{
    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    Question saveNewQuestion(QuestionDTO question);


    /**
     * Gets all Question objects in database.
     *
     * @return List of all Question objects in database, if any.
     */
    List<Question> getAllQuestions();


    /**
     * Get all questions that have an ID in a list.
     *
     * @param questionIDs List of question IDs.
     * @return            List of questions with corresponding IDs.
     */
    List<Question> getAllQuestionsInList(List<Long> questionIDs);


    /**
     * Gets the Question with the corresponding ID.
     *
     * @param questionID The ID of the question to fetch.
     * @return           Question with corresponding question ID.
     */
    Question getQuestionById(Long questionID);


    /**
     * Update a matching Question.
     *
     * @param updatedQuestion        Updated question.
     */
    void updateQuestion(QuestionDTO updatedQuestion);


    /**
     * Deletes a Question with a corresponding ID.
     *
     * @param questionID ID of the Question to delete.
     */
    void deleteQuestionByID(Long questionID);
}
