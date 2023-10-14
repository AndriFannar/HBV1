package is.hi.hbv501g.hbv1.Services;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;


/**
 * Service class for Staff objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-10-01
 * @version 1.0
 */
public interface StaffService
{
    /**
     * Find all Staff objects saved to database, if any.
     *
     * @return List of all Staff objects in database, if any.
     */
    List<Staff> findAll();


    /**
     * Save a new Staff object to database.
     *
     * @param staff Staff object to save.
     * @return      Saved Staff object.
     */
    Staff save(Staff staff);


    /**
     * Delete a Staff object from database by ID.
     *
     * @param staffID Unique ID of the Staff object to delete.
     */
    void delete(Long staffID);


    /**
     * Find Staff object by e-mail.
     *
     * @param email E-mail of Staff object to find.
     * @return      Staff object with matching e-mail, if any.
     */
    Staff findByEmail(String email);


    /**
     * Find a Staff object by unique ID.
     *
     * @param staffID Unique ID of Staff object to find.
     * @return        Staff member with corresponding ID, if any.
     */
    Staff findById(Long staffID);


    /**
     * Finds Staff members by physiotherapist.
     *
     * @param physiotherapist Search for physiotherapists or general staff.
     * @return                Staff objects with matching role, if any.
     */
    List<Staff> findByIsPhysiotherapist(boolean physiotherapist);


    /**
     * Update Staff member.
     *
     * @param staffID           Unique ID of Staff object to update.
     * @param name              Updated name, if any.
     * @param email             Updated e-mail, if any.
     * @param password          Updated password, if any.
     * @param phNumber          Updated phone number, if any.
     * @param isPhysiotherapist Updated job title, if any.
     * @param isAdmin           Updated role, if any.
     * @param specialization    Updated specialization, if any.
     * @param description       Updated description, if any.
     */
    void updateStaff(Long staffID, String name, String email, String password, String phNumber, boolean isPhysiotherapist, boolean isAdmin, String specialization, String description);


    /**
     * Log in given Staff object.
     *
     * @param staff Staff member to log in.
     * @return      Logged in Staff member.
     */
    Staff login(Staff staff);
}
