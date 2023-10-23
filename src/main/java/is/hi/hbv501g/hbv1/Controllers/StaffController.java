package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Services.StaffService;
import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.StaffService;

import is.hi.hbv501g.hbv1.Services.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

import java.util.List;


/**
 * Controller for Staff objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-10-01
 * @version 1.0
 */
@Deprecated
public class StaffController
{
    // Variables.
    private StaffService staffService;
    private WaitingListService waitingListService;


    /**
     * Construct a new StaffController.
     *
     * @param sS StaffService linked to controller.
     */
    @Autowired
    public StaffController(StaffService sS, WaitingListService wS)
    {
        this.staffService = sS;
        this.waitingListService = wS;
    }


    /**
     * Redirects to the homepage of the staff
     *
     * @param session used to for accessing staff session data
     * @param model   used to populate staff data for the view
     * @return        Redirect.
     */
    @RequestMapping(value="/staffIndex", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model)
    {
        Staff sessionUser = (Staff) session.getAttribute("LoggedInUser");
        System.out.println(sessionUser);

        if(sessionUser != null)
        {
            session.setAttribute("LoggedInUser", sessionUser);
            model.addAttribute("LoggedInUser", sessionUser);

            List<WaitingListRequest> requests;

            if(sessionUser.isAdmin() || !sessionUser.isPhysiotherapist())
            {
                requests = waitingListService.getRequests();
            }
            else
            {
                requests = waitingListService.getRequestByPhysiotherapist(sessionUser);
            }

            model.addAttribute("requests", requests);

            return "staffIndex";
        }
        return "redirect:/";
    }


    /**
     * Delete WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to delete.
     * @return          Redirect.
     */
    @RequestMapping(value = "/deleteRequest/{requestID}", method = RequestMethod.GET)
    public String deleteRequest(@PathVariable("requestID") Long requestID, Model model)
    {
        // If WaitingListRequest exists, delete.
        WaitingListRequest exists = waitingListService.getRequestByID(requestID);

        if (exists != null) {
            waitingListService.deleteRequest(requestID);
        }

        return "redirect:/staffIndex";
    }


    /**
     * Accept WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to accept.
     * @return          Redirect.
     */
    @RequestMapping(value = "/acceptRequest/{requestID}", method = RequestMethod.GET)
    public String acceptRequest(@PathVariable("requestID") Long requestID, Model model)
    {
        // If WaitingListRequest exists, accept.
        WaitingListRequest exists = waitingListService.getRequestByID(requestID);

        if (exists != null)
        {
            waitingListService.acceptRequest(requestID);
        }

        return "redirect:/staffIndex";
    }


    /**
     * Log out Staff.
     *
     * @param session Current session.
     * @param model   Page Model.
     * @return        Redirect to front page.
     */
    @RequestMapping(value="/logOutStaff", method=RequestMethod.GET)
    public String logOutGET(HttpSession session, Model model)
    {
        // Get currently logged in staff.
        Staff sessionUser = (Staff) session.getAttribute("LoggedInUser");

        // If staff is logged in, log out.
        if(sessionUser != null)
        {
            session.removeAttribute("LoggedInUser");
        }

        return "redirect:/";
    }

    // **** Will be enabled once HTML pages get set up **** //
    /**
     * Create a new Staff object.
     *
     * @param staff Staff to create.
     * @return      Redirect.
     */
    /*@RequestMapping(value = "/", method = RequestMethod.POST)
    public String createStaff(Staff staff, BindingResult result, Model model, HttpSession session)
    {
        if(result.hasErrors())
        {
            return "redirect:/signUp";
        }

        Staff exists = staffService.findByEmail(staff.getEmail());

        // If no errors, and Staff object does not exist with same e-mail, create.
        if(exists == null)
        {
            staffService.save(staff);

            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "redirect:/";
        }

        return "redirect:/";
    }*/

    /**
     * Get log in page for Staff.
     *
     * @param staff Staff to log in.
     * @param model used to populate data for the view
     * @return      staffLogin, which is the login page for staff.
     */
    @RequestMapping(value="/staffLogin", method = RequestMethod.GET)
    public String loginGet(Staff staff, Model model)
    {
        return "staffLogin";
    }


    /**
     * Logs in staff
     *
     * @param staff   Staff to log in
     * @param result  captures and handles validation errors
     * @param model   used to populate data for the view
     * @param session used to for accessing staff session data
     * @return        staffLogin, login page
     */
    @RequestMapping(value="/staffLogin", method = RequestMethod.POST)
    public String LoginPOST(@Validated Staff staff, BindingResult result,  Model model, HttpSession session){

      if(result.hasErrors()){
        return "staffLogin";
      }
      Staff exists = staffService.login(staff);

      if(exists != null){
        session.setAttribute("LoggedInUser", exists);
        model.addAttribute("LoggedInUser", exists);
        return "redirect:/staffIndex";
      }
      FieldError error = new FieldError("staff", "email", "Vitlaust netfang eða lykilorð");
      result.addError(error);
      return "staffLogin";
    }


    /**
     * Update WaitingListRequest.
     *
     * @param requestID Unique ID of Staff member to update.
     * @return          Redirect.
     */
     /*@RequestMapping(value = "/updateStaff", path = "{staffID}", method = RequestMethod.PUT)
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
     }*/

}
