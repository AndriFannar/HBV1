package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;
import is.hi.hbv501g.hbv1.Services.QuestionService;
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
    private QuestionService questionService;
    private WaitingListService waitingListService;


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
     * @return Page with a list of Questionnaires in the system.
     */
    @RequestMapping(value = "/questionnaireOverview", method = RequestMethod.GET)
    public String getQuestionnaireOverview(Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaire();
            model.addAttribute("questionnaires", questionnaires);
            model.addAttribute("questionnaire", new Questionnaire());

            return "questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Create a new Questionnaire.
     *
     * @return Saves a new Questionnaire to the database.
     */
    @RequestMapping(value = "/createQuestionnaire", method = RequestMethod.POST)
    public String createQuestionnaire(Questionnaire questionnaire, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            questionnaireService.saveQuestionnaire(questionnaire);

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Display Questionnaire on registration form.
     *
     * @return Redirect back to page.
     */
    @RequestMapping(value = "/displayOnForm/{questionnaireID}", method = RequestMethod.GET)
    public String displayOnForm(@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            questionnaireService.displayOnForm(questionnaireID);

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Delete a Questionnaire.
     *
     * @return Redirect back to page.
     */
    @RequestMapping(value = "/deleteQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String deleteQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            questionnaireService.deleteQuestionnaireById(questionnaireID);

            return "redirect:/questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Edit an existing Questionnaire
     *
     * @return Page where the user can edit a Questionnaire.
     */
    @RequestMapping(value = "/editQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String editQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            Questionnaire questionnaire = questionnaireService.getQuestionnaire(questionnaireID);

            if(questionnaire == null) return "redirect:/questionnaireOverview";

            model.addAttribute("questionnaire", questionnaire);

            List<Question> questions = questionService.getQuestions();
            model.addAttribute("questions", questions);
            model.addAttribute("question", new Question());

            return "viewQuestionnaire";
        }

        return "redirect:/";
    }


    /**
     * Add a Question to a Questionnaire.
     *
     * @return Redirect back to page.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/addQuestion/{questionID}", method = RequestMethod.GET)
    public String addQuestion(@PathVariable("questionID") Long questionID, @PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            questionnaireService.addQuestionToList(questionID, questionnaireID);

            return "redirect:/editQuestionnaire/" + questionnaireID;
        }

        return "redirect:/";
    }


    /**
     * Remove a Question from a Questionnaire.
     *
     * @return Redirect back to page.
     */
    @RequestMapping(value = "editQuestionnaire/{questionnaireID}/removeFromQuestionnaire/{questionID}", method = RequestMethod.GET)
    public String removeQuestion(@PathVariable("questionID") Long questionID ,@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null && loggedInUser.isAdmin())
        {
            questionnaireService.removeQuestionFromList(questionID, questionnaireID);

            return "redirect:/editQuestionnaire/" + questionnaireID;
        }

        return "redirect:/";
    }

    /**
     * Form for answering a Questionnaire.
     *
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "/answerQuestionnaire/{questionnaireID}", method = RequestMethod.GET)
    public String getQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID, Model model, HttpSession session)
    {
        // Find and add corresponding Questionnaire to model.
        Questionnaire form = questionnaireService.getQuestionnaire(questionnaireID);

        model.addAttribute("questionnaire", form);

        return "answerQuestionnaire";
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "/answerQuestionnaire", method = RequestMethod.POST)
    public String answerQuestionnaire(Questionnaire questionnaire, Model model, BindingResult result, HttpSession session)
    {
        User patient = (User) session.getAttribute("LoggedInUser");

        if(patient != null)
        {
            System.out.println("Questionnaire: " + questionnaire);
            System.out.println("Question size: " + questionnaire.getQuestions().size());
            if(!questionnaire.getQuestions().isEmpty()) System.out.println("Question: " + questionnaire.getQuestions().get(0).toString());

            Long requestID = patient.getWaitingListRequest().getId();
            System.out.println("Current request: " + requestID);

            waitingListService.addQuestionnaireAnswers(requestID, questionnaire);
        }

        return "redirect:/";
    }
}
