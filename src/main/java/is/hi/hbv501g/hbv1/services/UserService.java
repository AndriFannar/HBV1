package is.hi.hbv501g.hbv1.services;

import java.util.List;

import is.hi.hbv501g.hbv1.persistence.entities.dto.LoginDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.SignUpDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.UserDTO;
import is.hi.hbv501g.hbv1.persistence.entities.enums.UserRole;
import is.hi.hbv501g.hbv1.persistence.entities.User;


/**
 * Service class for User objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-25
 * @version 2.0
 */
public interface UserService
{
    /**
     * Save a new User to database.
     *
     * @param signUpDTO SignUp object to create new User from.
     * @return          Saved User object.
     */
    User saveNewUser(SignUpDTO signUpDTO);


    /**
     * Find all User objects saved to database, if any.
     *
     * @return List of all User objects in database, if any.
     */
    List<User> getAllUsers();


    /**
     * Find a User object by unique ID.
     *
     * @param userID Unique ID of User object to find.
     * @return       User with corresponding ID, if any.
     */
    User getUserByID(Long userID);


    /**
     * Find User object by e-mail.
     *
     * @param email E-mail of User object to find.
     * @return      User object with matching e-mail, if any.
     */
    User getUserByEmail(String email);


    /**
     * Find User object by SSN.
     *
     * @param ssn SSN of User to find.
     * @return    User with corresponding SSN, if any.
     */
    User getUserBySSN(String ssn);


    /**
     * Finds User by current UserRole.
     *
     * @param role            Search for User with matching UserRole.
     * @param includeElevated Include Users with a higher role.
     * @return                List of User objects with matching role, if any.
     */
    List<User> getUserByRole(UserRole role, boolean includeElevated);


    /**
     * Update User.
     *
     * @param updatedUser User with updated info.
     */
    void updateUser(UserDTO updatedUser);


    /**
     * Delete a User object from database by ID.
     *
     * @param userID Unique ID of the User object to delete.
     */
    void deleteUserByID(Long userID);


    /**
     * Log in given User.
     *
     * @param loginDTO Log in info.
     * @return         Logged in User.
     */
    User logInUser(LoginDTO loginDTO);


    /**
     * Checks if SSN is valid.
     *
     * @param ssn Social Security Number that is used to sign up
     * @return    String with error message if SSN is invalid
     */
    String validateSSN(String ssn);


    /**
     * Checks if password is valid
     *
     * @param password Password that is used to sign up
     * @return         String with error message if password is invalid
     */
    String validatePassword(String password);


    /**
     * Checks if e-mail is valid.
     *
     * @param email Email that is used to sign up.
     * @return      String with error message if e-mail is invalid
     */
    String validateEmail(String email);


    /**
     * Checks if phone number is valid.
     *
     * @param phoneNumber Phone number that is used to sign up.
     * @return            String with error message if phone number is invalid
     */
    String validatePhoneNumber(String phoneNumber);
}
