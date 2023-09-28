package is.hi.hbv501g.hbv1.Controllers;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import is.hi.hbv501g.hbv1.Servecies.PatientService;

@Controller
public class HomeController {
    
    private PatientService patientService;

    @Autowired
    public HomeController(PatientService uS){
        this.patientService = uS;
    }

    @RequestMapping("/")
    public String index(Model model){
        List<Patient> allPatients = patientService.findAll();
        model.addAttribute("patient", allPatients);
        return "index";
    }
    
}
