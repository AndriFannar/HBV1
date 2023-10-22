package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

import is.hi.hbv501g.hbv1.Services.UserService;

import java.util.List;


/**
 * Controller for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @author  Sigurður Örn Gunnarsson, sog6@hi.is.
 * @since   2023-09-27
 * @version 2.0
 */
@Controller
public class UserController
{
    // Variables.
    private UserService userService;
    private WaitingListService waitingListService;
    /*private boolean editingName = false;
    private boolean editingKennitala = false;

    @GetMapping("/editName")
    public String editName() {
        editingName = true;
        // Redirect back to the user page.
        return "redirect:/userPage"; }

    @GetMapping("/editKennitala")
    public String editKennitala() {
        editingKennitala = true;
        // Redirect back to the user page.
        return "redirect:/userPage"; }*/

    /**
     * Construct a new PatientController.
     *
     * @param userService        UserService linked to controller.
     * @param waitingListService WaitingListService linked to controller.
     */
    @Autowired
    public UserController(UserService userService, WaitingListService waitingListService)
    {
        this.userService = userService;
        this.waitingListService = waitingListService;
    }


    /**
     * Get login page.
     *
     * @param model Used to populate data for the view.
     * @return      Login page.
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String loadPages(Model model, HttpSession session)
    {
        User exists = (User) session.getAttribute("LoggedInUser");

        if(exists != null)
        {
            session.setAttribute("LoggedInUser", exists);

            if(exists.isStaff())
            {
                return "redirect:/staffIndex";
            }

            return "redirect:/patientIndex";
        }

        return "redirect:/login";
    }


    /**
     * Get page with form to sign up a new User.
     *
     * @param user  User to register.
     * @param model Used to populate data for the view.
     * @return      Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(User user, Model model)
    {
        model.addAttribute("user", new User());
        //model.addAttribute("waitingListRequest", new WaitingListRequest());
        return "newUser";
    }


    /**
     * Sign up a new Patient.
     *
     * @param user    Patient to register.
     * @param result  captures and handles validation errors
     * @param model   used to populate data for the view
     * @param session used to for accessing patient session data
     * @return        Redirect.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(@Validated User user, BindingResult result,  Model model, HttpSession session)
    {
        String errKen = userService.validateSsn(user);
        String errPass = userService.validatePassword(user);
        String errEmail = userService.validateEmail(user);
        String errPhN = userService.validatePhoneNumber(user);

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
        if(user.getName().length() == 0){
            FieldError error = new FieldError("patient", "name", "Vantar nafn");
            result.addError(error);
        }
        if(user.getAddress().length() == 0){
            FieldError error = new FieldError("patient", "address", "Vantar heimilsfang");
            result.addError(error);
        }
        if(!errPhN.isEmpty()){
            FieldError error = new FieldError("patient", "phoneNumber", errPhN);
            result.addError(error);
        }


        if(result.hasErrors())
        {
            model.addAttribute("user", user);
            return "newUser";
        }

        User exists = userService.findByEmail(user.getEmail());

        // If no errors, and Patient does not exist, save.
        if(exists == null)
        {
            userService.save(user);
            session.setAttribute("LoggedInUser", user);
            model.addAttribute("LoggedInUser", user);
            model.addAttribute("waitingListRequest", new WaitingListRequest());
            return "patientIndex";
        }

        return "newUser";
    }


    /**
     * Get login page.
     *
     * @param user  User to log in.
     * @param model Used to populate data for the view.
     * @return      Login page.
     */
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginGET(User user, Model model, HttpSession session){
        //model.addAttribute("showUpdateForm", false); // Initially hide the update form
        return "login";
    }


    /**
     * Logs in patient
     *
     * @param user    User to log in
     * @param result  captures and handles validation errors
     * @param model   used to populate data for the view
     * @param session used to for accessing patient session data
     * @return        Redirect.
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String loginPOST(User user, BindingResult result,  Model model, HttpSession session)
    {
        if(result.hasErrors())
        {
        return "login";
        }

        User exists = userService.login(user);

        if(exists != null)
        {
            session.setAttribute("LoggedInUser", exists);
            return "redirect:/";
        }

      FieldError error = new FieldError("user", "email", "Vitlaust netfang eða lykilorð");
      result.addError(error);
      return "login";
    }


    /**
     * Redirects to the homepage of the patient.
     *
     * @param session Used to for accessing patient session data.
     * @param model   Used to populate patient data for the view.
     * @return        Redirect.
     */
    @RequestMapping(value = "/patientIndex", method = RequestMethod.GET)
    public String patientIndex(HttpSession session, Model model)
    {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if (sessionUser != null)
        {
            model.addAttribute("LoggedInUser", sessionUser);

            WaitingListRequest request = waitingListService.getRequestByPatient(sessionUser);
            model.addAttribute("request", request);

            return "patientIndex";
        }
        return "redirect:/";
    }

    /**
     * Delete User.
     *
     * @param userID ID of User to delete.
     * @return       Redirect.
     */
    @RequestMapping(value = "/deleteUser/{userID}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userID") Long userID, Model model)
    {
        userService.delete(userID);

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
        boolean showUpdateForm = (boolean) model.getAttribute("showUpdateForm");
        model.addAttribute("showUpdateForm", !showUpdateForm);
        return "patientIndex";
    }

    /**
     * Replace old user info in the database with new user info.
     *
     * @param model          Model for page.
     * @param updatedUser    Current user.
     * @param session        Current HttpSession.
     *
     * @return LoggedInUser page.
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPatientInformation(@ModelAttribute("LoggedInUser") User updatedUser, Model model, HttpSession session) {
        model.addAttribute("waitingListRequest", new WaitingListRequest());
        // Retrieve the existing patient from the session or database
        User existingUser = (User) session.getAttribute("LoggedInUser");
        if (existingUser == null) {
            // Handle the case where the patient is not in the session
            return "redirect:/";
        }
        // Update only the fields that have changed
        existingUser.setName(updatedUser.getName());
        existingUser.setSsn(updatedUser.getSsn());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        // Save the updated patient information back to the database
        userService.updateUser(existingUser);
        return "patientIndex";
    }

    /**
     * For logging out the current user
     *
     * @param request HttpServletRequest
     * @return        Home page
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        // Invalidate the user's session
        request.getSession().invalidate();
        // Redirect to the login page
        return "redirect:/";
    }

    /**
     * Redirects to the homepage of staff members
     *
     * @param session used to for accessing staff session data
     * @param model   used to populate staff data for the view
     * @return        Redirect.
     */
    @RequestMapping(value="/staffIndex", method=RequestMethod.GET)
    public String staffIndex(HttpSession session, Model model)
    {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if(sessionUser != null)
        {
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
        waitingListService.deleteRequest(requestID);

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
        waitingListService.acceptRequest(requestID);

        return "redirect:/staffIndex";
    }

}
