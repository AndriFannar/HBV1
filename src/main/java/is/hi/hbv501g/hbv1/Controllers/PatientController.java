package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.PatientService;
import jakarta.servlet.http.HttpSession;


@Controller
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService uS){
        this.patientService = uS;
    }

    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(Patient patient, Model model){
        return "newUser";
    }

    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(Patient patient, BindingResult result,  Model model, HttpSession session){
        if(result.hasErrors()){
            return "redirect:/signUp";
        }
        Patient exists = patientService.findByEmail(patient.getEmail());
        if(exists == null){
            patientService.save(patient);
            
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "redirect:/loggedin";
        }
        return "redirect:/";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginPost(Patient patient, Model model){
        return "login";
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
