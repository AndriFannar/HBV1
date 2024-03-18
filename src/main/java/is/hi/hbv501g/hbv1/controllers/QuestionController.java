package is.hi.hbv501g.hbv1.controllers;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.ResponseWrapper;
import is.hi.hbv501g.hbv1.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API to handle QuestionDTO objects.
 * Append /question to the base URL to access these endpoints.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 *          Converted to REST API 2024-02-15.
 * @since   2023-11-07
 * @version 1.0
 */
@RestController
@RequestMapping(path = "api/v1/question")
public class QuestionController
{
    // Variables.
    private final QuestionService questionService;


    /**
     * Construct a new QuestionController.
     *
     * @param questionService QuestionService linked to controller.
     */
    @Autowired
    public QuestionController(QuestionService questionService)
    {
        this.questionService = questionService;
    }


    /**
     * Get all Questions saved to the API.
     * Append /getAll to the base URL to access this endpoint.
     *
     * @return List of all Questions saved to the API as a List of QuestionDTO.
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<QuestionDTO>>> getAllQuestions()
    {
        List<QuestionDTO> questions = questionService.getAllQuestions().stream().map(QuestionDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(questions), HttpStatus.OK);
    }


    /**
     * Get all Questions with corresponding ID from a List of IDs.
     * Append /getAllInList to the base URL to access this endpoint.
     *
     * @param questionIDs IDs of all Questions to fetch.
     * @return            List of all Questions with corresponding IDs as a List of QuestionDTO.
     */
    @RequestMapping(value = "/getAllInList", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<QuestionDTO>>> getAllQuestionsInList(@RequestParam List<Long> questionIDs)
    {
        List<QuestionDTO> questions = questionService.getAllQuestionsInList(questionIDs).stream().map(QuestionDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(questions), HttpStatus.OK);
    }


    /**
     * Create a new Question and save it to storage.
     * Append /create to the base URL to access this endpoint.
     *
     * @param question QuestionDTO object to save.
     * @return         HTTPStatus 200.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<QuestionDTO>> saveQuestion(@RequestBody QuestionDTO question)
    {
        questionService.saveNewQuestion(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Update a stored Question.
     * Append /update to the base URL to access this endpoint.
     *
     * @param updatedQuestion QuestionDTO with updated info.
     * @return                HTTPStatus 200.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<QuestionDTO>> updateQuestion(@RequestBody QuestionDTO updatedQuestion)
    {
        // Update Question.
        questionService.updateQuestion(updatedQuestion);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Delete a Question from storage.
     * Append /deleteQuestion/{questionID} to the base URL to access this endpoint.
     *
     * @param questionID ID of Question to delete.
     * @return           HTTPStatus 200 if successful.
     *                   HTTPStatus 404 if Question is not found.
     *                   HTTPStatus 409 if Question has dependencies.
     */
    @RequestMapping(value = "/deleteQuestion/{questionID}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseWrapper<QuestionDTO>> deleteQuestion(@PathVariable("questionID") Long questionID)
    {
        // Get Question from database.
        Question exists = questionService.getQuestionById(questionID);

        if (exists != null)
        {
            // If Question has no connected Questionnaires, then delete.
            if (exists.getQuestionnaires().isEmpty())
            {
                questionService.deleteQuestionByID(questionID);

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