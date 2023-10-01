package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Servecies.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.PatientService;
import jakarta.servlet.http.HttpSession;


@Controller
public class StaffController {

    private StaffService staffService;

    @Autowired
    public StaffController(StaffService sS){
        this.staffService = sS;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createStaff(Staff staff, BindingResult result, Model model, HttpSession session)
    {
        if(result.hasErrors()){
            return "redirect:/signUp";
        }
        Staff exists = staffService.findByEmail(staff.getEmail());
        if(exists == null){
            staffService.save(staff);

            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "redirect:/";
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

    /**
     * Update WaitingListRequest.
     *
     * @param requestID Unique ID of Staff member to update.
     * @return          Redirect.
     */
     @RequestMapping(value = "/updateStaff", path = "{staffID}", method = RequestMethod.PUT)
     public String updateRequest(@PathVariable("staffID") Long requestID, String name, String email, String password, String phNumber, boolean isPhysiotherapist, boolean isAdmin, String specialization, String description, BindingResult result, Model model)
     {
         if(result.hasErrors())
         {
            return "redirect:/updateStaff";
         }

         // If no errors and Staff object exists, update.
         Staff exists = staffService.findById(requestID);
         if(exists != null)
         {
            staffService.updateStaff(requestID, name, email, password, phNumber, isPhysiotherapist, isAdmin, specialization, description);
         }

         return "redirect:/";
     }

}
