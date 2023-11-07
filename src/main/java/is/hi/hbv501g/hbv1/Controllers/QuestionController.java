package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Services.QuestionService;
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
     * Get all Question objects in database.
     *
     * @return List of all Question objects in database.
     */
    @RequestMapping(value = "/questionOverview", method = RequestMethod.GET)
    public String getQuestions(Model model)
    {
        // Get all Question objects and display.
        List<Question> questions = questionService.getQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("question", new Question());
        return "viewQuestionnaire";
    }


    /**
     * Create a new Question.
     *
     * @param question Question object to save.
     * @return         Redirect.
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String saveQuestion(Question question, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "redirect:/questionOverview";
        }

        // Add Question to database if no errors.
        questionService.saveQuestion(question);

        return "redirect:/questionOverview";
    }


    /**
     * Delete Question.
     *
     * @param questionID ID of Question to delete.
     * @return           Redirect.
     */
    @RequestMapping(value = "/deleteQuestion/{questionID}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("questionID") Long questionID, Model model) {
        // If Question exists, delete.
        Question exists = questionService.getQuestionById(questionID);

        if (exists != null) {
            questionService.deleteQuestionById(questionID);
        }

        return "redirect:/questionOverview";
    }
}
