package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Controller for Question.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-11-07
 * @version 1.0
 */
@Controller
public class QuestionController
{
    // Variables.
    private QuestionService questionService;


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
     * @return         Redirect.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/addQuestion", method = RequestMethod.POST)
    public String saveQuestion(@PathVariable("questionnaireID") Long questionnaireID, Question question, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "redirect:/questionOverview";
        }

        // Add Question to database if no errors.
        questionService.saveQuestion(question);

        return "redirect:/editQuestionnaire/" + questionnaireID;
    }


    /**
     * Delete Question.
     *
     * @param questionID ID of Question to delete.
     * @return           Redirect.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/deleteQuestion/{questionID}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("questionnaireID") Long questionnaireID, @PathVariable("questionID") Long questionID, Model model) {
        // If Question exists, delete.
        Question exists = questionService.getQuestionById(questionID);

        if (exists != null) {
            questionService.deleteQuestionById(questionID);
        }

        return "redirect:/editQuestionnaire/" + questionnaireID;
    }
}
