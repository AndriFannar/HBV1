package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Servecies.StaffService;

import is.hi.hbv501g.hbv1.Servecies.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@Controller
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
     * Get index page for staff.
     *
     * @param session Current session.
     * @param model   Page Model.
     * @return        Redirect to staffIndex.
     */
    @RequestMapping(value="/staffIndex", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model)
    {
        Staff sessionUser = staffService.findByEmail("afk6@hi.is");
        //Staff sessionUser = (Staff) session.getAttribute("LoggedInUser");

        if(sessionUser != null)
        {
            session.setAttribute("LoggedInUser", sessionUser);
            model.addAttribute("LoggedInUser", sessionUser);

            List<WaitingListRequest> requests;

            if(sessionUser.isAdmin())
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

        if (exists != null) {
            System.out.println("******Accept");
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
     * Get log inpage for Staff.
     *
     * @param staff Staff to log in.
     * @return      Redirect.
     */
    /*@RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginPost(Staff staff, Model model)
    {
        return "login";
    }*/


    /**
     * Log in Staff.
     *
     * @param session Session to log Staff into.
     * @return        Redirect.
     */
    /*@RequestMapping(value="/loggedInUser", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model)
    {
        Staff sessionUser = (Staff) session.getAttribute("LoggedInUser");

        if(sessionUser != null)
        {
            model.addAttribute("LoggedInUser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }*/


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
