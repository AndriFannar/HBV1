package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import is.hi.hbv501g.hbv1.Persistence.Repositories.StaffRepository;
import is.hi.hbv501g.hbv1.Services.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * Service class implementation for Staff objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-10-01
 * @version 1.0
 */
@Service
public class StaffServiceImplementation implements StaffService
{
    // Variables.
    private StaffRepository staffRepository;


    /**
     * Constructs a new StaffServiceImplementation.
     *
     * @param staffRepository StaffRepository linked to service.
     */
    @Autowired
    public StaffServiceImplementation(StaffRepository staffRepository)
    {
        this.staffRepository = staffRepository;
    }


    /**
     * Find all Staff objects saved to database, if any.
     *
     * @return List of all Staff objects in database, if any.
     */
    @Override
    public List<Staff> findAll()
    {
        return (List<Staff>)(List<?>)staffRepository.findAll();
    }


    /**
     * Save a new Staff object to database.
     *
     * @param staff Staff object to save.
     * @return      Saved Staff object.
     */
    @Override
    public Staff save(Staff staff)
    {
        return staffRepository.save(staff);
    }


    /**
     * Delete a Staff object from database by ID.
     *
     * @param staffID Unique ID of the Staff object to delete.
     */
    @Override
    public void delete(Long staffID)
    {
        staffRepository.deleteById(staffID);
    }


    /**
     * Find Staff object by e-mail.
     *
     * @param email E-mail of Staff object to find.
     * @return      Staff object with matching e-mail, if any.
     */
    @Override
    public Staff findByEmail(String email)
    {
        return staffRepository.findByEmail(email);
    }


    /**
     * Find a Staff object by unique ID.
     *
     * @param staffID Unique ID of Staff object to find.
     * @return        Staff member with corresponding ID, if any.
     */
    @Override
    public Staff findById(Long staffID)
    {
        return staffRepository.findStaffById(staffID);
    }


    /**
     * Finds Staff members by physiotherapist.
     *
     * @param physiotherapist Search for physiotherapists or general staff.
     * @return                Staff objects with matching role, if any.
     */
    public List<Staff> findByIsPhysiotherapist(boolean physiotherapist)
    {
        return staffRepository.findStaffByIsPhysiotherapist(physiotherapist);
    }


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
    @Override
    @Transactional
    public void updateStaff(Long staffID, String name, String email, String password, String phNumber, boolean isPhysiotherapist, boolean isAdmin, String specialization, String description)
    {
        Staff staff = staffRepository.findStaffById(staffID);
        if (staff != null) {
            if (name != null && !Objects.equals(staff.getName(), name)) staff.setName(name);
            if (email != null && !Objects.equals(staff.getEmail(), email)) staff.setEmail(email);
            if (password != null && !Objects.equals(staff.getPassword(), password)) staff.setPassword(password);
            if (phNumber != null && !Objects.equals(staff.getPhoneNumber(), phNumber)) staff.setPhoneNumber(phNumber);
            if (!Objects.equals(staff.isPhysiotherapist(), isPhysiotherapist))
                staff.setPhysiotherapist(isPhysiotherapist);
            if (!Objects.equals(staff.isAdmin(), isAdmin)) staff.setAdmin(isAdmin);
            if (specialization != null && !Objects.equals(staff.getSpecialization(), specialization))
                staff.setSpecialization(specialization);
            if (description != null && !Objects.equals(staff.getDescription(), description))
                staff.setDescription(description);
        }
    }


    /**
     * Log in given Staff object.
     *
     * @param staff Staff member to log in.
     * @return      Logged in Staff member.
     */
    @Override
    public Staff login(Staff staff)
    {
        Staff doesExist = findByEmail(staff.getEmail());
        if(doesExist != null)
        {
            if(doesExist.getPassword().equals(staff.getPassword()))
            {
                return doesExist;
            }
        }
        return null;
    }
}
