package is.hi.hbv501g.hbv1.Persistence.Repositories;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository class for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long >
{
    /**
     * Save a Patient object to database.
     *
     * @param patient must not be {@literal null}.
     * @return        Saved object.
     */
    Patient save(Patient patient);


    /**
     * Delete Patient with corresponding ID.
     *
     * @param patientID must not be {@literal null}.
     */
    void deleteById(Long patientID);


    /**
     * Finds Patient by unique E-mail.
     *
     * @param email E-mail of patient to find.
     * @return      Patient with matching e-mail, if any.
     */
    Patient findByEmail(String email);


    /**
     * Find a Patient object by unique ID.
     *
     * @param patientID Unique ID of Patient object to find.
     * @return          Patient with corresponding ID, if any.
     */
    Patient findPatientById(Long patientID);


    /**
     * Finds all Patient objects in database.
     *
     * @return List of all Patient objects in database, if any.
     */
    List<Patient> findAll();

    Patient findByKennitala(String kennitala);
}
