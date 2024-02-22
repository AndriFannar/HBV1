package is.hi.hbv501g.hbv1.controllers;

import is.hi.hbv501g.hbv1.persistence.entities.*;
import is.hi.hbv501g.hbv1.persistence.entities.dto.*;
import is.hi.hbv501g.hbv1.persistence.entities.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import is.hi.hbv501g.hbv1.services.UserService;

import java.util.*;
import java.util.stream.Collectors;


/**
 * API for User objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 *          Converted to a REST API 2024-02-15.
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

    /**
     * Construct a new UserController.
     *
     * @param userService          UserService linked to controller.
     */
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
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
            return new ResponseEntity<>(new ResponseWrapper<>(user), HttpStatus.CREATED);
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
     * Delete User from API.
     *
     * @param userID  ID of User to delete.
     * @return        HTTP Status 200.
     */
    @RequestMapping(value = "/delete/{userID}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userID") Long userID)
    {
        userService.deleteUserByID(userID);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Get User.
     *
     * @param userID  ID of User to get.
     * @return        Redirect.
     */
    @RequestMapping(value = "view/{userID}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseWrapper<UserDTO>> getUser(@PathVariable("userID") Long userID)
    {
        // Get User to view.
        User viewUser = userService.getUserByID(userID);

        if (viewUser != null)
        {
            UserDTO user = new UserDTO(viewUser);
            return new ResponseEntity<>(new ResponseWrapper<>(user), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update User.
     *
     * @param requestingUserID ID of User making the update.
     * @param updatedUser      UserDTO with updated info.
     */
    @RequestMapping(value = "/update/{requestingUserID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<User>> updateUser(@PathVariable("requestingUserID") Long requestingUserID, @RequestBody UserDTO updatedUser)
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
            return new ResponseEntity<>(new ResponseWrapper<>(errorResponse), HttpStatus.BAD_REQUEST);
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
     * Gets a list of all Users saved in the API.
     *
     * @return List of all saved Users.
     */
    @RequestMapping(value="/getAll", method=RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getAllUsers()
    {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(userDTOs), HttpStatus.OK);
    }


    /**
     * Gets a list of all Users saved in the API with a specified UserRole.
     *
     * @return List of all saved Users with specified UserRole.
     */
    @RequestMapping(value="/getByRole", method=RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<User>>> getUsersByRole(@RequestBody UserRole userRole)
    {
        List<User> users = userService.getUserByRole(userRole, false);

        return new ResponseEntity<>(new ResponseWrapper<>(users), HttpStatus.OK);
    }


    /**
     * Gets a list of all Users saved in the API with a specified UserRole or higher.
     *
     * @return List of all saved Users with specified UserRole or higher.
     */
    @RequestMapping(value="/getByRoleElevated", method=RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<User>>> getUsersByRoleElevated(@RequestBody UserRole userRole)
    {
        List<User> users = userService.getUserByRole(userRole, true);

        return new ResponseEntity<>(new ResponseWrapper<>(users), HttpStatus.OK);
    }
}
