package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Services.PatientService;

import java.util.List;


/**
 * Controller for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @author  Sigurður Örn Gunnarsson, sog6@hi.is.
 * @since   2023-09-27
 * @version 1.0
 */
@Controller
public class PatientController
{
    // Variables.
    private PatientService patientService;
    private StaffService staffService;
    private boolean editingName = false;
    private boolean editingKennitala = false;

    // Add your other controller methods here.

    @GetMapping("/editName")
    public String editName() {
        editingName = true;
        return "redirect:/userPage"; // Redirect back to the user page.
    }

    @GetMapping("/editKennitala")
    public String editKennitala() {
        editingKennitala = true;
        return "redirect:/userPage"; // Redirect back to the user page.
    }

    /**
     * Construct a new PatientController.
     *
     * @param patientService PatientService linked to controller.
     */
    @Autowired
    public PatientController(PatientService patientService, StaffService staffService)
    {
        this.patientService = patientService;
        this.staffService = staffService;
    }


    /**
     * Get page with form to sign up a new Patient.
     *
     * @return        Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(Patient patient/*, Model model*/)
    {
        return "newUser";
    }


    /**
     * Sign up a new Patient.
     *
     * @param patient Patient to register.
     * @return        Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(Patient patient, BindingResult result,  Model model , HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/signUp";
        }
        Patient exists = patientService.findByEmail(patient.getEmail());
        // If no errors, and Patient does not exist, save.
        if(exists == null)
        {
            patientService.save(patient);
            session.setAttribute("LoggedInUser", patient);
            System.out.println(session.getAttribute("LoggedInUser"));
            model.addAttribute("LoggedInUser", patient);
            return "LoggedInUser";
        }
        return "redirect:/";
    }


    /**
     * Get login page.
     *
     * @return Login page.
     */
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginGET(Patient patient, Model model, HttpSession session){
        //model.addAttribute("showUpdateForm", false); // Initially hide the update form
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String LoginPOST(Patient patient, BindingResult result,  Model model, HttpSession session){
        if(result.hasErrors()){
            return "login";
        }
        Patient exists = patientService.login(patient);
        if(exists != null){
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "LoggedInUser";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model) {
        Patient sessionUser = (Patient) session.getAttribute("LoggedInUser");
        if (sessionUser != null) {
            model.addAttribute("LoggedInUser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }

    /**
     * Get user info update.
     *
     * @param model Model for page.
     *
     * @return LoggedInUser page.
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String toggleUpdateForm(Model model) {
        System.out.println("Nú vil ég geta uppfært notendaupplýsingarnar!!");
        boolean showUpdateForm = (boolean) model.getAttribute("showUpdateForm");
        System.out.println("Show update form" + showUpdateForm);
        model.addAttribute("showUpdateForm", !showUpdateForm);
        return "LoggedInUser";
    }

    /**
     * Replace old user info in the database with new patient info.
     *
     * @param model          Model for page.
     * @param updatedPatient Current patient.
     * @param session        Current HttpSession.
     *
     * @return LoggedInUser page.
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPatientInformation(@ModelAttribute("LoggedInUser") Patient updatedPatient, Model model, HttpSession session) {
        // Retrieve the existing patient from the session or database
        Patient existingPatient = (Patient) session.getAttribute("LoggedInUser");

        if (existingPatient == null) {
            // Handle the case where the patient is not in the session
            return "redirect:/";
        }

        // Update only the fields that have changed
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setKennitala(updatedPatient.getKennitala());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());

        // Save the updated patient information back to the database
        patientService.updatePatient(existingPatient);
        return "LoggedInUser";
    }

    /**
     * Get a list of physiotherapist for the basic waiting list registration.
     *
     * @param model Model for page.
     *
     * @return LoggedInUser page.
     */
    @RequestMapping(value = "/registerForBasicList", method = RequestMethod.GET)
    public String registerForBasicWaitingList(Model model) {
        // Retrieve the existing patient from the session or database
        List<Staff> physiotherapists = staffService.findByIsPhysiotherapist(true);
        model.addAttribute("physiotherapists", physiotherapists);
        return "LoggedInUser";
    }


}
