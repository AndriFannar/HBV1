package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * API for Question.
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
     * Create a new Question.
     *
     * @param question Question object to save.
     * @return         HttpStatus 200.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> saveQuestion(@RequestBody Question question)
    {
        questionService.saveNewQuestion(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Delete Question.
     *
     * @param questionID ID of Question to delete.
     * @return           Redirect.
     */
    @RequestMapping(value = "/deleteQuestion/{questionID}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("questionID") Long questionID)
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
