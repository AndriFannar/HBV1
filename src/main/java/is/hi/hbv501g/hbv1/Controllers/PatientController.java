package is.hi.hbv501g.hbv1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String loginGET(Patient patient/* , Model model*/){
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



    @RequestMapping(value="/loggedin", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model) {
        Patient sessionUser = (Patient) session.getAttribute("LoggedInUser");
        if(sessionUser != null){
            model.addAttribute("LoggedInUser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }
}
