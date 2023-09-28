package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.PatientService;


@Controller
public class WaitingListController {

    private PatientService patientService;

    @Autowired
    public WaitingListController(PatientService uS){
        this.patientService = uS;
    }


    @RequestMapping(value="/createRequest", method = RequestMethod.GET)
    public String createRequest(Patient patient, Model model){
        return "createRequest";
    }




    
}
