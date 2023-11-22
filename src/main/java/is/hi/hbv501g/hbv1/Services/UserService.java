package is.hi.hbv501g.hbv1.Services;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;


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
     * Save a new User object to database.
     *
     * @param user User object to save.
     * @return     Saved User object.
     */
    User saveNewUser(User user);


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
     * @param role Search for User with matching UserRole.
     * @return     List of User objects with matching role, if any.
     */
    public List<User> getUserByRole(User.UserRole role);


    /**
     * Update User.
     *
     * @param userID      ID of the User to update.
     * @param updatedUser User with updated info.
     */
    void updateUser(Long userID, User updatedUser);


    /**
     * Delete a User object from database by ID.
     *
     * @param userID Unique ID of the User object to delete.
     */
    void deleteUserByID(Long userID);


    /**
     * Log in given User object.
     *
     * @param user User to log in.
     * @return     Logged in User.
     */
    User logInUser(User user);


    /**
     * Checks if SSN is valid.
     *
     * @param user User that is trying to sign up
     * @return     String with error message if SSN is invalid
     */
    String validateSSN(User user);


    /**
     * Checks if password is valid
     *
     * @param user User that is trying to sign up
     * @return     String with error message if password is invalid
     */
    String validatePassword(User user);


    /**
     * Checks if e-mail is valid.
     *
     * @param user User that is trying to sign up.
     * @return     String with error message if e-mail is invalid
     */
    String validateEmail(User user);


    /**
     * Checks if phone number is valid.
     *
     * @param user User that is trying to sign up.
     * @return     String with error message if phone number is invalid
     */
    String validatePhoneNumber(User user);
}
