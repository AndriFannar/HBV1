package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * @param questionnaireID ID of Questionnaire the user was viewing. Redirects back to overview of that Questionnaire.
     * @param question        Question object to save.
     * @param result          BindingResult of form.
     * @return                Redirect.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/addQuestion", method = RequestMethod.POST)
    public String saveQuestion(@PathVariable("questionnaireID") Long questionnaireID, Question question, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "redirect:/questionOverview";
        }

        // Add Question to database if no errors.
        questionService.saveNewQuestion(question);

        return "redirect:/editQuestionnaire/" + questionnaireID;
    }


    /**
     * Delete Question.
     *
     * @param questionnaireID ID of Questionnaire the user was viewing. Redirects back to overview of that Questionnaire.
     * @param questionID      ID of Question to delete.
     * @param session         Current HttpSession.
     * @return                Redirect.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/deleteQuestion/{questionID}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("questionnaireID") Long questionnaireID, @PathVariable("questionID") Long questionID, HttpSession session) {
        // Get Question from database.
        Question exists = questionService.getQuestionById(questionID);

        if (exists != null)
        {
            // If Question has no connected Questionnaires, then delete.
            if (exists.getQuestionnaires().isEmpty())
            {
                questionService.deleteQuestionById(questionID);
            }

            // If not, then add the QuestionnaireID to the session to display message on editQuestionnaire page.
            else
            {
                session.setAttribute("questionError", exists.getId());
            }
        }

        return "redirect:/editQuestionnaire/" + questionnaireID;
    }
}
