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
import java.util.Objects;


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
            FieldError error = new FieldError( "user", "ssn", errKen);
            result.addError(error);
        }
        if(!errEmail.isEmpty()){
            FieldError error = new FieldError( "user", "email", errEmail);
            result.addError(error);
        }
        if(!errPass.isEmpty()){
            FieldError error = new FieldError("user", "password", errPass);
            result.addError(error);
        }
        if(user.getName().length() == 0){
            FieldError error = new FieldError("user", "name", "Vantar nafn");
            result.addError(error);
        }
        if(user.getAddress().length() == 0){
            FieldError error = new FieldError("user", "address", "Vantar heimilsfang");
            result.addError(error);
        }
        if(!errPhN.isEmpty()){
            FieldError error = new FieldError("user", "phoneNumber", errPhN);
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

            return "redirect:/";
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


            model.addAttribute("newRequest", new WaitingListRequest());

            List<User> staff = userService.findByIsPhysiotherapist(true);
            model.addAttribute("physiotherapists", staff);

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
     * View User.
     *
     * @param userID ID of User to view.
     * @return       Redirect.
     */
    @RequestMapping(value = "/viewUser/{userID}", method = RequestMethod.GET)
    public String viewUser(@PathVariable("userID") Long userID, Model model, HttpSession session)
    {
        User user = (User) session.getAttribute("LoggedInUser");
        User viewUser = userService.findByID(userID);

        if (user != null)
        {
            model.addAttribute("LoggedInUser", user);
            System.out.println(user.isAdmin());
            model.addAttribute("user", viewUser);

            return "viewUser";
        }

        return "redirect:/";
    }


    /**
     * Update User.
     *
     * @param userID      Unique ID of User to update.
     * @param updatedUser User with updated info.
     * @return            Redirect.
     */
    @RequestMapping(value = "/updateUser/{userID}", method = RequestMethod.POST)
    public String updateRequest(@PathVariable("userID") Long userID, @ModelAttribute("user") User updatedUser, BindingResult result, HttpSession session)
    {
        User currentUser = (User) session.getAttribute("LoggedInUser");

        if(!Objects.equals(currentUser.getEmail(), updatedUser.getEmail()))
        {
            String errEmail = userService.validateEmail(updatedUser);

            System.out.println("Email check");

            if(!errEmail.isEmpty())
            {
                FieldError error = new FieldError( "user", "email", errEmail);
                result.addError(error);
            }
        }

        String errPhN = userService.validatePhoneNumber(updatedUser);

        if(updatedUser.getName().length() == 0)
        {
            FieldError error = new FieldError("user", "name", "Vantar nafn");
            result.addError(error);
            System.out.println("Name error");
        }
        if(updatedUser.getAddress().length() == 0)
        {
            FieldError error = new FieldError("user", "address", "Vantar heimilsfang");
            result.addError(error);
            System.out.println("Address error");
        }
        if(!errPhN.isEmpty())
        {
            FieldError error = new FieldError("user", "phoneNumber", errPhN);
            result.addError(error);
            System.out.println(errPhN);
        }

        if(result.hasErrors())
        {
            return "redirect:/viewUser/" + userID;
        }

        System.out.println(updatedUser);
        userService.updateUser(userID, updatedUser);

        User updated = userService.findByID(userID);

        session.setAttribute("LoggedInUser", updated);

        return "redirect:/viewUser/" + userID;
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
     * Redirects to user overview
     *
     * @param session used to for accessing staff session data
     * @param model   used to populate staff data for the view
     * @return        Redirect.
     */
    @RequestMapping(value="/userOverview", method=RequestMethod.GET)
    public String userOverview(HttpSession session, Model model)
    {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if(sessionUser != null)
        {
            model.addAttribute("LoggedInUser", sessionUser);

            List<User> users = userService.findAll();

            model.addAttribute("users", users);

            return "userOverview";
        }
        return "redirect:/";
    }


    /**
     * Make user staff.
     *
     * @param userID ID of User to update to delete.
     * @return       Redirect.
     */
    @RequestMapping(value = "/makeStaff/{userID}", method = RequestMethod.GET)
    public String makeStaff(@PathVariable("userID") Long userID, Model model)
    {
        userService.changeRole(userID, true, false, false);

        return "redirect:/userOverview";
    }
}
