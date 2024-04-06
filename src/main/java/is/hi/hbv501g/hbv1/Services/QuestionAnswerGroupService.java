package is.hi.hbv501g.hbv1.services;

import is.hi.hbv501g.hbv1.persistence.entities.QuestionAnswerGroup;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionAnswerGroupDTO;

import java.util.List;


/**
 * Service class for QuestionAnswerGroup objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
public interface QuestionAnswerGroupService
{
    /**
     * Saves a new QuestionAnswerGroup to database.
     *
     * @param questionAnswerGroupDto QuestionAnswerGroup to save.
     * @return                       Saved QuestionAnswerGroup.
     */
    QuestionAnswerGroup saveNewQuestionAnswerGroup(QuestionAnswerGroupDTO questionAnswerGroupDto);


    /**
     * Gets all QuestionAnswerGroup objects in database.
     *
     * @return List of all QuestionAnswerGroup objects in database, if any.
     */
    List<QuestionAnswerGroup> getAllQuestionAnswerGroup();


    /**
     * Gets the QuestionAnswerGroup with the corresponding ID.
     *
     * @param questionAnswerGroupID The ID of the QuestionAnswerGroup to fetch.
     * @return                      QuestionAnswerGroup with corresponding question ID.
     */
    QuestionAnswerGroup getQuestionAnswerGroupById(Long questionAnswerGroupID);


    /**
     * Update a matching QuestionAnswerGroup.
     *
     * @param updatedQuestionAnswerGroupDto Updated QuestionAnswerGroup.
     */
    void updateQuestionAnswerGroup(QuestionAnswerGroupDTO updatedQuestionAnswerGroupDto);


    /**
     * Deletes a QuestionAnswerGroup with a corresponding ID.
     *
     * @param questionAnswerGroupID ID of the QuestionAnswerGroup to delete.
     */
    void deleteQuestionAnswerGroupByID(Long questionAnswerGroupID);
}
