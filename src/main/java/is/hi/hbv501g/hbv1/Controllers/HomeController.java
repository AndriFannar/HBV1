package is.hi.hbv501g.hbv1.Controllers;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import is.hi.hbv501g.hbv1.Services.PatientService;


/**
 * Controller for front page.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-25
 * @version 1.0
 */
@Controller
public class HomeController
{
    // Variables.
    private PatientService patientService;

    private WaitingListService waitingListService; // Is accessing other Services ok?


    /**
     * Construct a new WaitingListController.
     *
     * @param wS WaitingListService linked to controller.
     */
    @Autowired
    public HomeController(PatientService uS, WaitingListService wS)
    {
        this.patientService = uS;
        this.waitingListService = wS;
    }


    /**
     * Load in front page.
     *
     * @param model   Model for page.
     * @param session Current HttpSession.
     * @return        Front page.
     */
    @RequestMapping("/")
    public String index(Model model, HttpSession session){
        List<Patient> allPatients = patientService.findAll();
        model.addAttribute("patient", allPatients);

        Patient patient = (Patient) session.getAttribute("LoggedInUser");
        model.addAttribute("login", patient);

        if (patient != null)
        {
            WaitingListRequest waitingListRequest = patient.getWaitingListRequest();
            if (waitingListRequest != null)
            {
                model.addAttribute("request", waitingListRequest);
            }
        }

        return "index";
    }
    
}
