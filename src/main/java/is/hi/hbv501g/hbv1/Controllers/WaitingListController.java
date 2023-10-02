package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Servecies.PatientService;
import is.hi.hbv501g.hbv1.Servecies.WaitingListService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
    private PatientService patientService;


    /**
     * Construct a new WaitingListController.
     *
     * @param wS WaitingListService linked to controller.
     */
    @Autowired
    public WaitingListController(WaitingListService wS, PatientService pS)
    {
        this.waitingListService = wS;
        this.patientService = pS;
    }


    /**
     * Form for creating a new WaitingListRequest.
     *
     * @return createRequest page.
     */
    @RequestMapping(value = "/createRequest", method = RequestMethod.GET)
    public String waitingListForm(Model model)
    {
        model.addAttribute("waitingListRequest", new WaitingListRequest());
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
            return "redirect:/newRequest";
        }

        //Get Patient that is logged in, and link to WaitingListRequest.
        Patient patient = (Patient) session.getAttribute("LoggedInUser");

        if (patient != null && patient.getWaitingListRequest() == null)
        {
            waitingListRequest.setPatient(patient);

            waitingListService.createNewRequest(waitingListRequest);

            patientService.updatePatient(patient.getId(), null, null, null, null, null, waitingListRequest);

            System.out.println("WLR:" + patient.getName());

            //patient.setWaitingListRequest(waitingListRequest);
            // call update in service


        }

        return "redirect:/";
    }

    // **** To be enabled when HTML templates are ready **** //

    /*/**
     * Update WaitingListRequest.
     *
     * @param requestID Unique ID of request to update.
     * @return          Redirect.
     *
    @RequestMapping(value = "/updateRequest", path = "{requestID}", method = RequestMethod.PUT)
    public String updateRequest(@PathVariable("requestID") Long requestID, Staff staff, String bodyPart, String description, boolean status, int questionnaireID, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "redirect:/updateRequest";
        }

        // If no errors and request exists, update.
        WaitingListRequest exists = waitingListService.getRequestByID(requestID);
        if(exists != null)
        {
            waitingListService.updateRequest(requestID, staff, bodyPart, description, status, questionnaireID);
        }

        return "redirect:/";
    }


    /**
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
     * @return List of WaitingListRequest objects.
     *
    @RequestMapping(value = "/homePage", method = RequestMethod.GET)
    public String getRequests(Model model)
    {
        // Get all requests and display.
        List<WaitingListRequest> requests = waitingListService.getRequests();
        model.addAttribute("requests", requests);
        return "homePage";

    }*/
}
