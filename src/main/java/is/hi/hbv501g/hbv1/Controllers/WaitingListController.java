package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.PatientService;
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
     * Form for creating a new registration for the basic waiting list.
     *
     * @return createRequest page.
     */
    @RequestMapping(value = "/registerForBasicList", method = RequestMethod.GET)
    public String basicWaitingListForm(Model model) {
        model.addAttribute("waitingListRequest", new WaitingListRequest());
        return "LoggedInUser";
    }


    /**
     * Create a new registration for the basic waiting list.
     *
     * @param waitingListRequest WaitingListRequest object to save.
     * @return                   Redirect.
     */
    @RequestMapping(value = "/registerForBasicList", method = RequestMethod.POST)
    public String newBasicListRegistry(@ModelAttribute("waitingListRequest") WaitingListRequest waitingListRequest, BindingResult result, Model model, HttpSession session)
    {
        //Get Patient that is logged in
        Patient sessionUser = (Patient) session.getAttribute("LoggedInUser");
        if (sessionUser != null && sessionUser.getWaitingListRequest() == null) {
            //Link logged in Patient to WaitingListRequest.
            model.addAttribute("LoggedInUser", sessionUser);
            waitingListRequest.setPatient(sessionUser);
            waitingListRequest.setStatus(true);
            // Update currently logged-in user's session info.
            waitingListService.updateRequest(waitingListRequest);
            return "LoggedInUser";
        }
        if (result.hasErrors()) {
            return "LoggedInUser";
        }

        else {
            // Handle the case where a request already exists for the patient
            model.addAttribute("LoggedInUser", sessionUser);
            model.addAttribute("error", "A request already exists for this patient.");
            return "LoggedInUser";
        }
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
