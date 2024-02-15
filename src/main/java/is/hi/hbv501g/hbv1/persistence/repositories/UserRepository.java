package is.hi.hbv501g.hbv1.persistence.repositories;

import is.hi.hbv501g.hbv1.persistence.entities.enums.UserRole;
import is.hi.hbv501g.hbv1.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository class for User objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 2.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long >
{
    /**
     * Save a User object to database.
     *
     * @param user must not be {@literal null}.
     * @return     Saved object.
     */
    @NonNull
    User save(@NonNull User user);


    /**
     * Delete User with corresponding ID.
     *
     * @param userID must not be {@literal null}.
     */
    void deleteById(@NonNull Long userID);


    /**
     * Finds User by unique E-mail.
     *
     * @param email E-mail of User to find.
     * @return      User with matching e-mail, if any.
     */
    User getByEmail(String email);


    /**
     * Find a User object by unique ID.
     *
     * @param userID Unique ID of User object to find.
     * @return       User with corresponding ID, if any.
     */
    @NonNull
    User getById(@NonNull Long userID);


    /**
     * Finds all User objects in database.
     *
     * @return List of all User objects in database, if any.
     */
    List<User> getAllByOrderByRoleDescNameAsc();


    /**
     * Finds User by role.
     *
     * @param role Search for User by UserRole.
     * @return     User objects with matching role, if any.
     */
    List<User> getByRole(UserRole role);


    /**
     * Finds User by role.
     *
     * @param role Search for User that has at least UserRole.
     * @return     User objects with matching role or above, if any.
     */
    List<User> getByRoleGreaterThanEqual(UserRole role);


    /**
     * Finds User by SSN.
     *
     * @param ssn SSN of User to find.
     * @return    User object with matching SSN, if any.
     */
    User getBySsn(String ssn);
}
