package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.PatientService;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;
import is.hi.hbv501g.hbv1.Services.StaffService;
import is.hi.hbv501g.hbv1.Services.WaitingListService;

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

import java.time.format.DateTimeFormatter;
import java.util.List;


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
    private WaitingListService waitingListService;
    private QuestionnaireService questionnaireService;
    private StaffService staffService;
    private PatientService patientService;


    /**
     * Construct a new WaitingListController.
     *
     * @param wS WaitingListService linked to controller.
     */
    @Autowired
    public WaitingListController(WaitingListService wS, QuestionnaireService qS, StaffService sS)
    {
        this.waitingListService = wS;
        this.questionnaireService = qS;
        this.staffService = sS;
    }


    /**
     * Form for creating a new registration for the basic waiting list.
     *
     * @return createRequest page.
     */
    @RequestMapping(value = "/createRequest", method = RequestMethod.GET)
    public String waitingListForm(Model model)
    {
        model.addAttribute("request", new WaitingListRequest());

        List<Staff> staff = staffService.findByIsPhysiotherapist(true);
        model.addAttribute("physiotherapists", staff);

        return "newRequest";
    }


    /**
     * Create a new WaitingListRequest.
     *
     * @param waitingListRequest WaitingListRequest object to save.
     * @return                   Redirect.
     */
    @RequestMapping(value = "/createRequest", method = RequestMethod.POST)
    public String createNewRequest(WaitingListRequest waitingListRequest, BindingResult result, Model model, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/createRequest";
        }

        //Get Patient that is logged in, and link to WaitingListRequest.
        Patient patient = (Patient) session.getAttribute("LoggedInUser");
        waitingListRequest.setPatient(patient);

        // If no errors, and request does not exist, create.
        WaitingListRequest exists = waitingListService.getRequestByPatient(waitingListRequest.getPatient());
        if(exists == null)
        {
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
        Staff user = (Staff) session.getAttribute("LoggedInUser");

        if (exists != null)
        {
            List<Staff> staff = staffService.findByIsPhysiotherapist(true);

            model.addAttribute("request", exists);
            model.addAttribute("physiotherapists", staff);
            model.addAttribute("LoggedInUser", user);

            int[] answers  = exists.getQuestionnaireAnswers();

            if(answers != null)
            {
                model.addAttribute("answers", answers);
            }

            return "viewRequest";
        }

        return "redirect:/staffIndex";
    }


    // **** To be enabled when HTML templates are ready **** //

    /**
     * Update WaitingListRequest.
     *
     * @param requestID Unique ID of request to update.
     * @return          Redirect.
     */
    @RequestMapping(value = "/updateRequest/{requestID}", method = RequestMethod.POST)
    public String updateRequest(@PathVariable("requestID") Long requestID, @ModelAttribute("request") WaitingListRequest updatedRequest, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "redirect:/viewRequest/" + requestID;
        }

        waitingListService.updateRequest(requestID, updatedRequest);


        System.out.println("Request Update:" + updatedRequest);

        return "redirect:/viewRequest/" + requestID;
    }


    /*/**
     * Delete WaitingListRequest.
     *
     * @param waitingListID ID of the request to delete.
     * @return              Redirect.
     *
    @RequestMapping(value = "/updateRequest", path = "{waitingListID}", method = RequestMethod.DELETE)
    public String deleteRequest(@PathVariable("waitingListID") Long waitingListID, Model model)
    {
        // If request exists, delete.
        WaitingListRequest exists = waitingListService.getRequestByID(waitingListID);

        if(exists != null)
        {
            waitingListService.deleteRequest(waitingListID);
        }

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
        return "LoggedInUser";

    }
}
