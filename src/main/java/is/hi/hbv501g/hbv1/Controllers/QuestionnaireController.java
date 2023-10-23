package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.QuestionnaireForm;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

import is.hi.hbv501g.hbv1.Services.WaitingListService;
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
 * Controller for Questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
@Controller
public class QuestionnaireController
{
    // Variables.
    private QuestionnaireService questionnaireService;
    private WaitingListService waitingListService;


    /**
     * Construct a new QuestionnaireController.
     *
     * @param qS QuestionnaireService linked to controller.
     */
    @Autowired
    public QuestionnaireController(QuestionnaireService qS, WaitingListService wS)
    {
        this.questionnaireService = qS;
        this.waitingListService = wS;
    }

    // **** Will be enabled when Questionnaires are implemented. **** //

    /**
     * Form for answering a Questionnaire.
     *
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "/questionnaire", method = RequestMethod.GET)
    public String getQuestionnaire(WaitingListRequest waitingLR, Model model, HttpSession session)
    {
        // Get ID of questionnaire to get from request.
        int questionnaireID = waitingLR.getQuestionnaireID();

        // Find and add corresponding Questionnaire to model.
        QuestionnaireForm form = questionnaireService.getQuestionnaire(questionnaireID);

        model.addAttribute("questionnaire", form);

        return "questionnaire";
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "/answerQuestionnaire", method = RequestMethod.POST)
    public String answerQuestionnaire(QuestionnaireForm form, Model model, BindingResult result, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/questionnaire";
        }

        Patient patient = (Patient) session.getAttribute("LoggedInUser");

        //waitingListService.updateRequest(patient.getWaitingListRequest().getId(), null, null, null, false, null, form, null);

        return "redirect:/";
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
        List<Question> questions = questionnaireService.getQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("question", new Question());
        return "questionOverview";
    }


    /**
     * Create a new Question.
     *
     * @param question Question object to save.
     * @return         Redirect.
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String addQuestion(Question question, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "redirect:/questionOverview";
        }

        // Add Question to database if no errors.
        questionnaireService.addQuestion(question);

        return "redirect:/questionOverview";
    }


    /**
     * Update Question.
     *
     * @param questionID     ID of Question to update.
     * @param questionString New string of Question, if any.
     * @param weight         New weight of Question, if any.
     * @param listID         New list IDs, if any.
     * @return               Redirect.
     *
    @RequestMapping(value = "/updateQuestion", path = "{questionID}", method = RequestMethod.PUT)
    public String updateQuestion(@PathVariable("questionID") Long questionID, String questionString, double weight, List<Integer> listID, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "redirect:/updateQuestion";
        }

        // If Question exists, update.
        Question exists = questionnaireService.getQuestionById(questionID);
        if(exists != null)
        {
            questionnaireService.updateQuestion(questionID, questionString, weight, listID);
        }

        return "redirect:/";
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
        Question exists = questionnaireService.getQuestionById(questionID);

        if (exists != null) {
            questionnaireService.deleteQuestionById(questionID);
        }

        return "redirect:/questionOverview";
    }
}
