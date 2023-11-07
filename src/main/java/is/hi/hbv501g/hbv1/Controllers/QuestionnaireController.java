package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;
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


    /**
     * Get a Questionnaire overview.
     *
     * @return Page with a list of Questionnaires in the system.
     */
    @RequestMapping(value = "/questionnaireOverview", method = RequestMethod.GET)
    public String getQuestionnaireOverview(Model model, HttpSession session)
    {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser.isAdmin())
        {
            List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaire();
            model.addAttribute("questionnaires", questionnaires);

            return "questionnaireOverview";
        }

        return "redirect:/";
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @return questionnaire page with Questionnaire object.
     */
    @RequestMapping(value = "/questionnaire", method = RequestMethod.GET)
    public String getQuestionnaire(WaitingListRequest waitingLR, Model model, HttpSession session)
    {
        // Get ID of questionnaire to get from request.
        Long questionnaireID = waitingLR.getQuestionnaireID();

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
        if(result.hasErrors())
        {
            return "redirect:/questionnaire";
        }

        User patient = (User) session.getAttribute("LoggedInUser");

        //waitingListService.updateRequest(patient.getWaitingListRequest().getId(), null, null, null, false, null, form, null);

        return "redirect:/";
    }
}
