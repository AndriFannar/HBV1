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
     * @param result             BindingResult of form
     * @param session            Current HttpSession.
     * @return                   Redirect.
     */
    @RequestMapping(value = "/createRequest", method = RequestMethod.POST)
    public String createNewRequest(WaitingListRequest waitingListRequest, BindingResult result, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/";
        }

        //Get Patient that is logged in, and link to WaitingListRequest.
        User patient = (User) session.getAttribute("LoggedInUser");
        waitingListRequest.setPatient(patient);

        // Check if User already has a Request linked.
        WaitingListRequest exists = waitingListService.getWaitingListRequestByPatient(waitingListRequest.getPatient());

        // If not, save the new Request.
        if(exists == null)
        {
            waitingListService.saveNewWaitingListRequest(waitingListRequest);

            // Get updated User info from database to update current HttpSession.
            User updatedUser = userService.getUserByID(patient.getId());

            // Make sure Java Spring fetches the User from database, otherwise the Hibernate session might get disconnected when checking this attribute later.
            if(updatedUser.getRole() != null)
            {
                // Update User in current HttpSession.
                session.setAttribute("LoggedInUser", updatedUser);
            }
        }

        return "redirect:/";
    }


    /**
     * View WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to view.
     * @param model     Model of page.
     * @param session   Current HttpSession.
     * @return          Redirect.
     */
    @RequestMapping(value = "/viewRequest/{requestID}", method = RequestMethod.GET)
    public String viewRequest(@PathVariable("requestID") Long requestID, Model model, HttpSession session)
    {
        // Get WaitingListRequest and User.
        WaitingListRequest exists = waitingListService.getWaitingListRequestByID(requestID);
        User user = (User) session.getAttribute("LoggedInUser");

        // Both Request and User must exist, and User must have a valid role.
        if ((exists != null) && (user != null) && (user.getRole() != null))
        {
            // User must be either a member of staff or the User to which the Request belongs.
            if((user.getRole() != User.UserRole.USER) || (Objects.equals(user.getWaitingListRequest().getId(), requestID)))
            {
                // Get all Users with Physiotherapist role and all questionnaires to display on update form.
                List<User> staff = userService.getUserByRole(User.UserRole.PHYSIOTHERAPIST);
                List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();

                // Add everything to page model.
                model.addAttribute("request", exists);
                model.addAttribute("physiotherapists", staff);
                model.addAttribute("questionnaires", questionnaires);
                model.addAttribute("LoggedInUser", user);

                return "viewRequest";
            }
        }

        return "redirect:/";
    }


    /**
     * Update WaitingListRequest.
     *
     * @param requestID      ID of WaitingListRequest to update.
     * @param updatedRequest WaitingListRequest with updated info.
     * @param result         BindingResult of form.
     * @param session        Current HttpSession.
     * @return               Redirect.
     */
    @RequestMapping(value = "/updateRequest/{requestID}", method = RequestMethod.POST)
    public String updateRequest(@PathVariable("requestID") Long requestID, @ModelAttribute("request") WaitingListRequest updatedRequest, BindingResult result, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/viewRequest/" + requestID;
        }

        // Update WaitingListRequest.
        waitingListService.updateWaitingListRequest(requestID, updatedRequest);

        User user = (User) session.getAttribute("LoggedInUser");

        // If the User updating the Request is not an Admin, then mark the Request as not accepted so a member of staff can review updates.
        if (!(user.getRole() == User.UserRole.STAFF)) waitingListService.updateWaitingListRequestStatus(requestID, false);

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
        waitingListService.updateWaitingListRequestStatus(requestID, true);

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
}
