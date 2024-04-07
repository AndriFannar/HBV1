package is.hi.hbv501g.hbv1.controllers;

import is.hi.hbv501g.hbv1.persistence.entities.QuestionAnswerGroup;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionAnswerGroupDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.ResponseWrapper;
import is.hi.hbv501g.hbv1.services.QuestionAnswerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API to handle QuestionAnswerGroupDTO objects.
 * Append /questionAnswerGroup to the base URL to access these endpoints.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
@RestController
@RequestMapping(path = "api/v1/questionAnswerGroup")
public class QuestionAnswerGroupController
{
    private final QuestionAnswerGroupService questionAnswerGroupService;

    /**
     * Construct a new QuestionAnswerGroupController.
     *
     * @param questionAnswerGroupService QuestionAnswerGroupService linked to Controller.
     */
    @Autowired
    public QuestionAnswerGroupController(QuestionAnswerGroupService questionAnswerGroupService) {
        this.questionAnswerGroupService = questionAnswerGroupService;
    }

    /**
     * Get all QuestionAnswerGroup saved to the API.
     * Append /getAll to the base URL to access this endpoint.
     *
     * @return List of all QuestionAnswerGroup saved to the API as a List of QuestionAnswerGroupDTO.
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<QuestionAnswerGroupDTO>>> getAllQuestionAnswerGroup()
    {
        List<QuestionAnswerGroupDTO> questionAnswerGroupDTOS = questionAnswerGroupService.getAllQuestionAnswerGroup().stream().map(QuestionAnswerGroupDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(questionAnswerGroupDTOS), HttpStatus.OK);
    }


    /**
     * Create a new QuestionAnswerGroup and save it to storage.
     * Append /create to the base URL to access this endpoint.
     *
     * @param questionAnswerGroup QuestionAnswerGroupDTO object to save.
     * @return                    HTTPStatus 200.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<QuestionAnswerGroupDTO>> saveQuestionAnswerGroup(@RequestBody QuestionAnswerGroupDTO questionAnswerGroup)
    {
        QuestionAnswerGroup savedQuestionAnswerGroup = questionAnswerGroupService.saveNewQuestionAnswerGroup(questionAnswerGroup);

        return new ResponseEntity<>(new ResponseWrapper<>(new QuestionAnswerGroupDTO(savedQuestionAnswerGroup)), HttpStatus.OK);
    }


    /**
     * Update a stored QuestionAnswerGroup.
     * Append /update to the base URL to access this endpoint.
     *
     * @param updatedQuestionAnswerGroup QuestionAnswerGroupDTO with updated info.
     * @return                           HTTPStatus 200.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<QuestionAnswerGroupDTO>> updateQuestionAnswerGroup(@RequestBody QuestionAnswerGroupDTO updatedQuestionAnswerGroup)
    {
        // Update Question.
        questionAnswerGroupService.updateQuestionAnswerGroup(updatedQuestionAnswerGroup);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Delete a QuestionAnswerGroup from storage.
     * Append /delete/{questionAnswerGroupID} to the base URL to access this endpoint.
     *
     * @param questionAnswerGroupID ID of QuestionAnswerGroup to delete.
     * @return                      HTTPStatus 200 if successful.
     *                              HTTPStatus 404 if Question is not found.
     *                              HTTPStatus 409 if Question has dependencies.
     */
    @RequestMapping(value = "/delete/{questionAnswerGroupID}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseWrapper<QuestionAnswerGroupDTO>> deleteQuestionAnswerGroup(@PathVariable("questionAnswerGroupID") Long questionAnswerGroupID)
    {
        // Get Question from database.
        QuestionAnswerGroup exists = questionAnswerGroupService.getQuestionAnswerGroupById(questionAnswerGroupID);

        if (exists != null)
        {
            // If Question has no connected Questionnaires, then delete.
            if (exists.getQuestions().isEmpty())
            {
                questionAnswerGroupService.deleteQuestionAnswerGroupByID(questionAnswerGroupID);

                return new ResponseEntity<>(HttpStatus.OK);
            }

            // If Question has dependencies then return a conflict.
            else
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
