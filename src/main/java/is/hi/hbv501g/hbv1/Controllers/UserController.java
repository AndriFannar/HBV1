package is.hi.hbv501g.hbv1.Controllers;

import is.hi.hbv501g.hbv1.Persistence.Entities.*;
import is.hi.hbv501g.hbv1.Persistence.Entities.DTOs.*;
import is.hi.hbv501g.hbv1.Persistence.Entities.Enums.UserRole;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

import is.hi.hbv501g.hbv1.Services.UserService;

import java.util.*;


/**
 * API for User objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @author  Sigurður Örn Gunnarsson, sog6@hi.is.
 * @author  Friðrik Þór Ólafsson, fto2@hi.is.
 * @since   2023-09-27
 * @version 3.0
 */
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController
{
    // Variables.
    private final UserService userService;
    private final WaitingListService waitingListService;
    private final QuestionnaireService questionnaireService;

    /**
     * Construct a new UserController.
     *
     * @param userService          UserService linked to controller.
     * @param waitingListService   WaitingListService linked to controller.
     * @param questionnaireService QuestionnaireService linked to controller.
     */
    @Autowired
    public UserController(UserService userService, WaitingListService waitingListService, QuestionnaireService questionnaireService)
    {
        this.userService = userService;
        this.waitingListService = waitingListService;
        this.questionnaireService = questionnaireService;
    }


    /**
     * Sign up a new User.
     *
     * @param signUpDTO SignUpDTO object to create User from.
     * @return          ResponseWrapper containing User signed up, or in case of errors, containing an ErrorResponse.
     */
    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<User>> signUp(@RequestBody SignUpDTO signUpDTO)
    {
        Map<String, String> errorMap = new HashMap<>();

        String error = userService.validateSSN(signUpDTO.getSsn());

        if (!error.isEmpty()) errorMap.put("ssn", error);

        error = userService.validatePassword(signUpDTO.getPassword());

        if (!error.isEmpty()) errorMap.put("password", error);

        error = userService.validateEmail(signUpDTO.getEmail());

        if (!error.isEmpty()) errorMap.put("email", error);

        error = userService.validatePhoneNumber(signUpDTO.getPhoneNumber());

        if (!error.isEmpty()) errorMap.put("phoneNumber", error);

        if(signUpDTO.getName().isEmpty()) errorMap.put("name", "Vantar nafn");

        if(signUpDTO.getAddress().isEmpty()) errorMap.put("address", "Vantar heimilisfang");


        // Check if errorMap has any errors, and if so, return them instead of the User.
        if(!errorMap.isEmpty())
        {
            ErrorResponse errorResponse = new ErrorResponse("Villa við nýskráningu", errorMap);
            return new ResponseEntity<>(new ResponseWrapper<>(errorResponse), HttpStatus.BAD_REQUEST);
        }

        User exists = userService.getUserByEmail(signUpDTO.getEmail());

        // If no errors, and Patient does not exist, save.
        if(exists == null)
        {
            User user = new User(signUpDTO);
            userService.saveNewUser(user);
            return new ResponseEntity<>(new ResponseWrapper<>(user), HttpStatus.OK);
        }

        errorMap.put("exists", "Notandi þegar til");
        ErrorResponse errorResponse = new ErrorResponse("Villa við nýskráningu", errorMap);
        return new ResponseEntity<>(new ResponseWrapper<>(errorResponse), HttpStatus.BAD_REQUEST);
    }


    /**
     * Logs in a User.
     *
     * @param loginDTO LogInDTO object that contains a User's login info.
     * @return         A ResponseWrapper containing the User that credentials belonged to,
     *                 or in case of errors, containing an ErrorResponse.
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<User>> loginPOST(@RequestBody LoginDTO loginDTO)
    {
        User exists = userService.logInUser(loginDTO);

        if(exists != null)
        {
            return new ResponseEntity<>(new ResponseWrapper<>(exists), HttpStatus.OK);
        }

        Map<String, String> error = new HashMap<>();
        error.put("login", "Rangt notendanafn eða lykilorð");

        return new ResponseEntity<>(new ResponseWrapper<>(new ErrorResponse("Villa við innskráningu", error)), HttpStatus.BAD_REQUEST);
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

        // Makes sure user is logged in and has User role.
        if (sessionUser != null && sessionUser.getRole() == UserRole.USER)
        {
            model.addAttribute("LoggedInUser", sessionUser);

            // Get user's WaitingListRequest.
            WaitingListRequest request = waitingListService.getWaitingListRequestByPatient(sessionUser);
            model.addAttribute("request", request);

            // If Request does not exist, get data to populate form.
            if(request == null)
            {
                // Get Questionnaires to display on form.
                List<Questionnaire> questionnaires = questionnaireService.getQuestionnairesOnForm();
                model.addAttribute("questionnaires", questionnaires);

                // Get Physiotherapists to display on form.
                List<User> staff = userService.getUserByRole(UserRole.PHYSIOTHERAPIST, true);
                model.addAttribute("physiotherapists", staff);

                model.addAttribute("newRequest", new WaitingListRequest());
            }

            return "patientIndex";
        }

        return "redirect:/";
    }


    /**
     * Delete User.
     *
     * @param userID  ID of User to delete.
     * @param session Current HttpSession.
     * @param request Current ServletRequest.
     * @return        Redirect.
     */
    @RequestMapping(value = "/deleteUser/{userID}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userID") Long userID, HttpSession session, HttpServletRequest request)
    {
        User user = (User) session.getAttribute("LoggedInUser");

        if (user != null)
        {
            // If User that is logged in and User to delete are the same, then invalidate login.
            if (Objects.equals(user.getId(), userID))
            {
                userService.deleteUserByID(userID);
                request.getSession().invalidate();
            }
            // If User is Admin (and is not deleting himself), then don't invalidate login and return to userOverview page.
            else if (user.getRole() == UserRole.ADMIN)
            {
                userService.deleteUserByID(userID);

                return "redirect:/userOverview";
            }

        }
        return "redirect:/";
    }


    /**
     * View User page.
     *
     * @param userID  ID of User to view.
     * @param model   Page model.
     * @param session Current HttpSession.
     * @return        Redirect.
     */
    @RequestMapping(value = "viewUser/{userID}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserDTO> viewUser(@PathVariable("userID") Long userID, Model model, HttpSession session)
    {
        // Get logged in User and User to view.
        User viewUser = userService.getUserByID(userID);

        if (viewUser != null)
        {
            UserDTO user = new UserDTO(viewUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update User.
     *
     * @param requestingUserID ID of User making the update.
     * @param updatedUser      UserDTO with updated info.
     */
    @RequestMapping(value = "/updateUser/{requestingUserID}", method = RequestMethod.PUT)
    public ResponseEntity<ErrorResponse> updateUser(@PathVariable("requestingUserID") Long requestingUserID, @RequestBody UserDTO updatedUser)
    {
        User userToUpdate = userService.getUserByID(updatedUser.getId());

        Map<String, String> errorMap = new HashMap<>();

        // Validate inputted info.
        if(!Objects.equals(userToUpdate.getEmail(), updatedUser.getEmail()))
        {
            String errEmail = userService.validateEmail(updatedUser.getEmail());

            if(!errEmail.isEmpty()) errorMap.put("email", errEmail);
        }

        String errPhN = userService.validatePhoneNumber(updatedUser.getPhoneNumber());

        if(!errPhN.isEmpty()) errorMap.put("phoneNumber", errPhN);

        if(updatedUser.getName().isEmpty()) errorMap.put("name", "Vantar nafn");

        if(updatedUser.getAddress().isEmpty()) errorMap.put("address", "Vantar heimilisfang");

        if(!errorMap.isEmpty())
        {
            ErrorResponse errorResponse = new ErrorResponse("Villa við breytingu á notendaupplýsingum", errorMap);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User requestingUser = userService.getUserByID(requestingUserID);

        // If User that is doing the update is admin, then update.
        if (requestingUser.getRole() == UserRole.ADMIN)
        {
            userService.updateUser(updatedUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        // If user that is doing the update is not admin and is updating himself.
        if(Objects.equals(updatedUser.getId(), requestingUserID))
        {
            // Make sure that the role is not updated (only admin can update role).
            updatedUser.setRole(null);
            userService.updateUser(updatedUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /**
     * Log out current User.
     *
     * @param request Current HttpServletRequest
     * @return        Redirect.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        // Invalidate the user's session.
        request.getSession().invalidate();

        // Redirect to start page.
        return "redirect:/";
    }


    /**
     * Gets the homepage of staff members.
     *
     * @param session Used to for accessing staff session data.
     * @param model   Used to populate staff data for the view.
     * @return        Redirect.
     */
    @RequestMapping(value="/staffIndex", method=RequestMethod.GET)
    public String staffIndex(HttpSession session, Model model)
    {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        // Make sure User is logged in and that the User has a valid role.
        if(sessionUser != null && sessionUser.getRole() != null)
        {
            // Only display page if User's role is not user.
            if (sessionUser.getRole() != UserRole.USER)
            {
                model.addAttribute("LoggedInUser", sessionUser);

                List<WaitingListRequest> requests;

                // If User is physiotherapist, then only get WaitingListRequests associated with that physiotherapist.
                if(sessionUser.getRole() == UserRole.PHYSIOTHERAPIST)
                {
                    requests = waitingListService.getWaitingListRequestByPhysiotherapist(sessionUser);
                }
                // Else, get all WaitingListRequests.
                else
                {
                    requests = waitingListService.getAllWaitingListRequests();
                }

                model.addAttribute("requests", requests);

                return "staffIndex";
            }
        }
        return "redirect:/";
    }


    /**
     * Redirects to user overview page.
     *
     * @param session Used to for accessing staff session data
     * @param model   Used to populate staff data for the view
     * @return        Redirect.
     */
    @RequestMapping(value="/userOverview", method=RequestMethod.GET)
    public String userOverview(HttpSession session, Model model)
    {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        // Make sure User is admin.
        if(sessionUser != null && sessionUser.getRole() == UserRole.ADMIN)
        {
            model.addAttribute("LoggedInUser", sessionUser);

            // Get all Users and display.
            List<User> users = userService.getAllUsers();

            model.addAttribute("users", users);

            return "userOverview";
        }

        return "redirect:/";
    }
}
