package is.hi.hbv501g.hbv1.Services;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;


/**
 * Service class for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-25
 * @version 1.0
 */
public interface PatientService
{
    /**
     * Find all Patient objects saved to database, if any.
     *
     * @return List of all Patient objects in database, if any.
     */
    List<Patient> findAll();


    /**
     * Save a new Patient object to database.
     *
     * @param patient Patient object to save.
     * @return        Saved Patient object.
     */
    Patient save(Patient patient);


    /**
     * Delete a Patient object from database by ID.
     *
     * @param patientID Unique ID of the Patient object to delete.
     */
    void delete(Long patientID);


    /**
     * Find Patient object by e-mail.
     *
     * @param email E-mail of Patient object to find.
     * @return      Patient object with matching e-mail, if any.
     */
    Patient findByEmail(String email);


    /**
     * Find a Patient object by unique ID.
     *
     * @param patientID Unique ID of Patient object to find.
     * @return          Patient with corresponding ID, if any.
     */
    Patient findByID(Long patientID);


    /**
     * Log in given Patient object.
     *
     * @param patient Patient to log in.
     * @return        Logged in Patient.
     */
    Patient login(Patient patient);



    /**
     * Update Patient.
     *
     * @param patientID          Unique ID of Patient object to update.
     * @param name               Updated name, if any.
     * @param email              Updated e-mail, if any.
     * @param password           Updated password, if any.
     * @param phNumber           Updated phone number, if any.
     * @param address            Updated address, if any.
     * @return                   Updated Patient.
     */
    Patient updatePatient(Long patientID, String name, String email, String password, String phNumber, String address);

    /**
     * Update Patient.
     *
     * @param patient  Patient to update
     */
    void updatePatient(Patient patient);
}
