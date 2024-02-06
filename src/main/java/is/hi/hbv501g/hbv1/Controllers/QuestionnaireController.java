package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

import is.hi.hbv501g.hbv1.Services.WaitingListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final QuestionnaireService questionnaireService;
    private final QuestionService questionService;
    private final WaitingListService waitingListService;


    /**
     * Construct a new QuestionnaireController.
     *
     * @param questionnaireService QuestionnaireService linked to controller.
     * @param questionService      QuestionService linked to controller.
     * @param waitingListService   WaitingListService linked to controller.
     */
    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, QuestionService questionService, WaitingListService waitingListService)
    {
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.waitingListService = waitingListService;
    }


    /**
     * Get a Questionnaire overview.
     *
     * @param model   Page model.
     * @param session Current HttpSession.
     * @return        Page with a list of Questionnaires in the system.
     */
    @RequestMapping(value = "/questionnaireOverview", method = RequestMethod.GET)
    public String getQuestionnaireOverview(Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only display page if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();
            model.addAttribute("questionnaires", questionnaires);
            model.addAttribute("questionnaire", new Questionnaire());

            model.addAttribute("questionnaireError", session.getAttribute("questionnaireError"));
            session.removeAttribute("questionnaireError");

            return "questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Create a new Questionnaire.
     *
     * @param questionnaire New Questionnaire.
     * @param session       Current HttpSession.
     * @return              Saves a new Questionnaire to the database.
     */
    @RequestMapping(value = "/createQuestionnaire", method = RequestMethod.POST)
    public String createQuestionnaire(Questionnaire questionnaire, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only create if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            questionnaireService.saveNewQuestionnaire(questionnaire);

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Toggle display Questionnaire on registration form.
     *
     * @param questionnaireID ID of Questionnaire to toggle
     * @param session         Current HttpSession
     * @return                Redirect back to page.
     */
    @RequestMapping(value = "/toggleDisplayOnForm/{questionnaireID}", method = RequestMethod.GET)
    public String toggleDisplayOnForm(@PathVariable("questionnaireID") Long questionnaireID, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only toggle if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            questionnaireService.toggleDisplayQuestionnaireOnForm(questionnaireID);

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Delete a Questionnaire.
     *
     * @param questionnaireID ID of Questionnaire to delete.
     * @param session         Current HttpSession.
     * @return                Redirect back to page.
     */
    @RequestMapping(value = "/deleteQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String deleteQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        Questionnaire questionnaire = questionnaireService.getQuestionnaireByID(questionnaireID);

        // Only delete if User is admin.
        if (questionnaire != null && loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            // If Questionnaire has no connected WaitingListRequests, then delete.
            if(questionnaire.getWaitingListRequests().isEmpty())
            {
                questionnaireService.deleteQuestionnaireByID(questionnaireID);
            }
            // Else add the QuestionnaireID to the session to display a message on questionnaireOverview page.
            else
            {
                session.setAttribute("questionnaireError", questionnaireID);
            }

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Get a page for editing an existing Questionnaire
     *
     * @param questionnaireID ID of Questionnaire to edit.
     * @param model           Page model.
     * @param session         Current HttpSession.
     * @return                Page where the user can edit a Questionnaire.
     */
    @RequestMapping(value = "/editQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String editQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only allow edit if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            Questionnaire questionnaire = questionnaireService.getQuestionnaireByID(questionnaireID);

            // If Questionnaire does not exist, redirect back.
            if(questionnaire == null) return "redirect:/questionnaireOverview";

            // Add Questionnaire to model.
            model.addAttribute("questionnaire", questionnaire);

            // Get all Questions in database to display.
            List<Question> questions = questionService.getAllQuestions();
            model.addAttribute("questions", questions);

            // Create a new Question if the User wants to add one.
            model.addAttribute("question", new Question());

            // If Question could not be deleted, show a message on page.
            model.addAttribute("questionError", session.getAttribute("questionError"));
            session.removeAttribute("questionError");

            return "viewQuestionnaire";
        }

        return "redirect:/";
    }


    /**
     * Add a Question to a Questionnaire.
     *
     * @param questionID      ID of Question to add to Questionnaire.
     * @param questionnaireID ID of Questionnaire to add Question to.
     * @param session         Current HttpSession.
     * @return                Redirect back to page.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/addQuestion/{questionID}", method = RequestMethod.GET)
    public String addQuestion(@PathVariable("questionID") Long questionID, @PathVariable("questionnaireID") Long questionnaireID, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only add question if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            questionnaireService.addQuestionToQuestionnaire(questionID, questionnaireID);

            return "redirect:/editQuestionnaire/" + questionnaireID;
        }

        return "redirect:/";
    }


    /**
     * Remove a Question from a Questionnaire.
     *
     * @param questionID      ID of Question to remove from Questionnaire.
     * @param questionnaireID ID of Questionnaire to remove Question from.
     * @param session         Current HttpSession.
     * @return                Redirect back to page.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/removeFromQuestionnaire/{questionID}", method = RequestMethod.GET)
    public String removeQuestion(@PathVariable("questionID") Long questionID , @PathVariable("questionnaireID") Long questionnaireID, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // Only remove Question if User is admin.
        if (loggedInUser != null && loggedInUser.getRole() == User.UserRole.ADMIN)
        {
            questionnaireService.removeQuestionFromQuestionnaire(questionID, questionnaireID);

            return "redirect:/editQuestionnaire/" + questionnaireID;
        }

        return "redirect:/";
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @param requestID ID of WaitingListRequest that the Questionnaire is from.
     * @param questionnaireID ID of Questionnaire to answer.
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "{requestID}/answerQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String getQuestionnaire(@PathVariable("requestID") Long requestID, @PathVariable("questionnaireID") Long questionnaireID, Model model)
    {
        // Find and add corresponding Questionnaire to model.
        Questionnaire form = questionnaireService.getQuestionnaireByID(questionnaireID);
        WaitingListRequest request = waitingListService.getWaitingListRequestByID(requestID);

        model.addAttribute("questionnaire", form);
        model.addAttribute("request", request);

        return "answerQuestionnaire";
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @param requestID     ID of WaitingListRequest the answers belong to.
     * @param questionnaire Questionnaire with answers.
     * @param session       Current HttpSession.
     * @return              Redirect.
     */
    @RequestMapping(value = "{requestID}/answerQuestionnaire", method = RequestMethod.POST)
    public String answerQuestionnaire(@PathVariable("requestID") Long requestID, Questionnaire questionnaire, HttpSession session)
    {
        User patient = (User) session.getAttribute("LoggedInUser");

        if(patient != null)
        {
            waitingListService.updateQuestionnaireAnswers(requestID, questionnaire);
        }

        return "redirect:/";
    }
}
