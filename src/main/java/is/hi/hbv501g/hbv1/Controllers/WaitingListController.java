package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.UserService;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Objects;


/**
 * Controller for WaitingListRequest.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Controller
public class WaitingListController
{
    // Variables.
    private final WaitingListService waitingListService;
    private final QuestionnaireService questionnaireService;
    private final UserService userService;


    /**
     * Construct a new WaitingListController.
     *
     * @param waitingListService   WaitingListService linked to controller.
     * @param questionnaireService QuestionnaireService linked to controller.
     * @param userService          UserService linked to controller.
     */
    @Autowired
    public WaitingListController(WaitingListService waitingListService, QuestionnaireService questionnaireService, UserService userService)
    {
        this.waitingListService = waitingListService;
        this.questionnaireService = questionnaireService;
        this.userService = userService;
    }


    /**
     * Create a new WaitingListRequest.
     *
     * @param waitingListRequest WaitingListRequest object to save.
     * @return                   Redirect.
     */
    @RequestMapping(value = "/createRequest", method = RequestMethod.POST)
    public String createNewRequest(WaitingListRequest waitingListRequest, BindingResult result, HttpSession session)
    {
        System.out.println("Request submitted: " + waitingListRequest.getId());
        System.out.println("Request submitted: " + waitingListRequest.getDescription());
        System.out.println("Request submitted: " + waitingListRequest.getQuestionnaire());
        if(result.hasErrors())
        {
            System.out.println("Error: " + result.getAllErrors());
            return "redirect:/";
        }

        //Get Patient that is logged in, and link to WaitingListRequest.
        User patient = (User) session.getAttribute("LoggedInUser");
        waitingListRequest.setPatient(patient);

        // If no errors, and request does not exist, create.
        WaitingListRequest exists = waitingListService.getRequestByPatient(waitingListRequest.getPatient());
        if(exists == null)
        {
            System.out.println("Does not already exist");
            waitingListService.createNewRequest(waitingListRequest);
        }

        return "redirect:/";
    }


    /**
     * View WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to view.
     * @return          Redirect.
     */
    @RequestMapping(value = "/viewRequest/{requestID}", method = RequestMethod.GET)
    public String viewRequest(@PathVariable("requestID") Long requestID, Model model, HttpSession session)
    {
        WaitingListRequest exists = waitingListService.getRequestByID(requestID);
        User user = (User) session.getAttribute("LoggedInUser");

        if ((exists != null) && (user != null))
        {
            if((user.isStaff()) || (Objects.equals(user.getWaitingListRequest().getId(), requestID))) {
                List<User> staff = userService.findByIsPhysiotherapist(true);
                List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaire();

                model.addAttribute("request", exists);
                model.addAttribute("physiotherapists", staff);
                model.addAttribute("questionnaires", questionnaires);
                model.addAttribute("LoggedInUser", user);

                List<Integer> answers = exists.getQuestionnaireAnswers();

                if (answers != null) {
                    model.addAttribute("answers", answers);
                }

                return "viewRequest";
            }
        }

        return "redirect:/";
    }

    /**
     * Update WaitingListRequest.
     *
     * @param requestID Unique ID of request to update.
     * @return          Redirect.
     */
    @RequestMapping(value = "/updateRequest/{requestID}", method = RequestMethod.POST)
    public String updateRequest(@PathVariable("requestID") Long requestID, @ModelAttribute("request") WaitingListRequest updatedRequest, BindingResult result, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/viewRequest/" + requestID;
        }

        waitingListService.updateRequest(requestID, updatedRequest);

        User user = (User) session.getAttribute("LoggedInUser");

        if (!user.isStaff()) waitingListService.updateRequestStatus(requestID, false);

        return "redirect:/viewRequest/" + requestID;
    }


    /**
     * Accept WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to accept.
     * @return          Redirect.
     */
    @RequestMapping(value = "/acceptRequest/{requestID}", method = RequestMethod.GET)
    public String acceptRequest(@PathVariable("requestID") Long requestID)
    {
        waitingListService.updateRequestStatus(requestID, true);

        return "redirect:/";
    }


    /**
     * Delete WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to delete.
     * @return          Redirect.
     */
    @RequestMapping(value = "/deleteRequest/{requestID}", method = RequestMethod.GET)
    public String deleteRequest(@PathVariable("requestID") Long requestID)
    {
        waitingListService.deleteRequest(requestID);

        return "redirect:/";
    }


    /**
     * Get all WaitingListRequest objects.
     *
     * @return List of WaitingListRequest objects.
     */
    @RequestMapping(value = "/registrationOverview", method = RequestMethod.GET)
    public String getRequests(Model model)
    {
        // Get all requests and display.
        List<WaitingListRequest> requests = waitingListService.getRequests();
        model.addAttribute("waitingListRequest", requests);
        return "patientIndex";

    }
}
