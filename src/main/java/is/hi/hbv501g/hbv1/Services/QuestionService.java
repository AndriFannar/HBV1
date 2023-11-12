package is.hi.hbv501g.hbv1.Services;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;

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
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionID The ID of the question to fetch.
     * @return           Question with corresponding question ID.
     */
    Question getQuestionById(Long questionID);


    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    Question saveQuestion(Question question);

    /**
     * Update a matching Question.
     *
     * @param questionID             ID of the question to update.
     * @param updatedQuestion        Updated question.
     */
    void updateQuestion(Long questionID, Question updatedQuestion);


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
}
