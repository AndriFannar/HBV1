package is.hi.hbv501g.hbv1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Servecies.PatientService;


/**
 * Controller for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-27
 * @version 1.0
 */
@Controller
public class PatientController
{
    // Variables.
    private PatientService patientService;


    /**
     * Construct a new PatientController.
     *
     * @param uS PatientService linked to controller.
     */
    @Autowired
    public PatientController(PatientService uS)
    {
        this.patientService = uS;
    }


    /**
     * Get page with form to sign up a new Patient.
     *
     * @param patient to register.
     * @param model   used to populate data for the view.
     * @return        Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(Patient patient, Model model)
    {
        model.addAttribute("patient", new Patient());
        //model.addAttribute("waitingListRequest", new WaitingListRequest());
        return "newUser";
    }


    /**
     * Sign up a new Patient.
     *
     * @param patient Patient to register.
     * @param result  captures and handles validation errors
     * @param model   used to populate data for the view
     * @param session used to for accessing patient session data
     * @return        Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(@Validated Patient patient, BindingResult result,  Model model, HttpSession session)
    {
        String errKen = patientService.validateKennitala(patient);
        String errPass = patientService.validatePassword(patient);
        String errEmail = patientService.validateEmail(patient);
        String errPhN = patientService.validatePhoneNumber(patient);

        if (!errKen.isEmpty()) {
            FieldError error = new FieldError( "patient", "kennitala", errKen);
            result.addError(error);
        }
        if(!errEmail.isEmpty()){
            FieldError error = new FieldError( "patient", "email", errEmail);
            result.addError(error);
        }
        if(!errPass.isEmpty()){
            FieldError error = new FieldError("patient", "password", errPass);
            result.addError(error);
        }
        if(patient.getName().length() == 0){
            FieldError error = new FieldError("patient", "name", "Vantar nafn");
            result.addError(error);
        }
        if(patient.getAddress().length() == 0){
            FieldError error = new FieldError("patient", "address", "Vantar heimilsfang");
            result.addError(error);
        }
        if(!errPhN.isEmpty()){
            FieldError error = new FieldError("patient", "phoneNumber", errPhN);
            result.addError(error);
        }


        if(result.hasErrors())
        {
            model.addAttribute("patient", patient);
            return "newUser";
        }

        patientService.save(patient);
            
        session.setAttribute("LoggedInUser", patient);

        model.addAttribute("LoggedInUser", patient);
        return "LoggedInUser";
    }


    /**
     * Get login page.
     *
     * @param patient patient to log in.
     * @param model   used to populate data for the view
     * @return        Login page.
     */
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginGET(Patient patient, Model model){
        return "login";
    }

    /**
     * Logs in patient
     *
     * @param patient patient to log in
     * @param result  captures and handles validation errors
     * @param model   used to populate data for the view
     * @param session used to for accessing patient session data
     * @return        Redirect.
     */
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
      FieldError error = new FieldError("patient", "email", "Vitlaust netfang eða lykilorð");
      result.addError(error);
      return "login";
    }


    /**
     * Redirects to the homepage of the patient.
     *
     * @param session used to for accessing patient session data.
     * @param model   used to populate patient data for the view.
     * @return        Redirect.
     */
    @RequestMapping(value="/loggedin", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model) {
        Patient sessionUser = (Patient) session.getAttribute("LoggedInUser");
        if(sessionUser != null){
            model.addAttribute("LoggedInUser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }

    /**
     * Delete Patient.
     *
     * @param patientID ID of Patient to delete.
     * @return          Redirect.
     */
    @RequestMapping(value = "/deletePatient/{patientID}", method = RequestMethod.GET)
    public String deletePatient(@PathVariable("patientID") Long patientID, Model model) {
        // If Patient exists, delete.
        Patient exists = patientService.findByID(patientID);

        if (exists != null) {
            patientService.delete(patientID);
        }

        return "redirect:/";
    }
}
