package is.hi.hbv501g.hbv1.Persistence.Repositories;

import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for Staff objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-10-01
 * @version 1.0
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long >
{
    /**
     * Save a Staff object to database.
     *
     * @param staff must not be {@literal null}.
     * @return      Saved object.
     */
    Staff save(Staff staff);

    /**
     * Delete Patient with corresponding ID.
     *
     * @param staffID must not be {@literal null}.
     */
    void deleteById(Long staffID);

    /**
     * Finds Patient by unique E-mail.
     *
     * @param email E-mail of patient to find.
     * @return      Patient with matching e-mail, if any.
     */
    Staff findByEmail(String email);

    /**
     * Find a Staff object by unique ID.
     *
     * @param staffID Unique ID of Staff object to find.
     * @return        Staff member with corresponding ID, if any.
     */
    Staff findStaffById(Long staffID);

    /**
     * Finds Staff members by physiotherapist.
     *
     * @param physiotherapist Search for physiotherapists or general staff.
     * @return                Staff objects with matching role, if any.
     */
    List<Staff> findStaffByIsPhysiotherapist(boolean physiotherapist);

    /**
     * Finds all Patient objects in database.
     *
     * @return List of all Patient objects in database, if any.
     */
    List<Staff> findAll();
}
