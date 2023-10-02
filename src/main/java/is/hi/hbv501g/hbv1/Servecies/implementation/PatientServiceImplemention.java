package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.List;
import java.util.Objects;

import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Servecies.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Repositories.PatientRepository;
import is.hi.hbv501g.hbv1.Servecies.PatientService;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class implementation for Patient objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Service
public class PatientServiceImplemention implements PatientService
{
    // Variables.
    private PatientRepository patientRepository;
    private WaitingListService waitingListService;


    /**
     * Constructs a new PatientServiceImplementation.
     *
     * @param patientRespotory PatientRepository linked to service.
     */
    @Autowired
    public PatientServiceImplemention(PatientRepository patientRespotory, WaitingListService waitingListService)
    {
        this.patientRepository = patientRespotory;
        this.waitingListService = waitingListService;
    }


    /**
     * Find all Patient objects saved to database, if any.
     *
     * @return List of all Patient objects in database, if any.
     */
    @Override
    public List<Patient> findAll()
    {
        return (List<Patient>)(List<?>)patientRepository.findAll();
    }


    /**
     * Save a new Patient object to database.
     *
     * @param patient Patient object to save.
     * @return        Saved Patient object.
     */
    @Override
    public Patient save(Patient patient)
    {
        return patientRepository.save(patient);
    }


    /**
     * Delete a Patient object from database by ID.
     *
     * @param patientID Unique ID of the Patient object to delete.
     */
    @Override
    public void delete(Long patientID)
    {
        patientRepository.deleteById(patientID);
    }


    /**
     * Log in given Patient object.
     *
     * @param patient Patient to log in.
     * @return        Logged in Patient.
     */
    @Override
    public Patient login(Patient patient)
    {
        Patient doesExist = findByEmail(patient.getEmail());
        if(doesExist != null)
        {
            if(doesExist.getPassword().equals(patient.getPassword()))
            {
                return doesExist;
            }
        }

        return null;
    }


    /**
     * Find Patient object by e-mail.
     *
     * @param email E-mail of Patient object to find.
     * @return      Patient object with matching e-mail, if any.
     */
    @Override
    public Patient findByEmail(String email)
    {
        return patientRepository.findByEmail(email);
    }


    /**
     * Find a Patient object by unique ID.
     *
     * @param patientID Unique ID of Patient object to find.
     * @return          Patient with corresponding ID, if any.
     */
    public Patient findByID(Long patientID)
    {
        return patientRepository.findPatientById(patientID);
    }


    /**
     * Update Patient.
     *
     * @param patientID          Unique ID of Patient object to update.
     * @param name               Updated name, if any.
     * @param email              Updated e-mail, if any.
     * @param password           Updated password, if any.
     * @param phNumber           Updated phone number, if any.
     * @param address            Updated address, if any.
     * @param waitingListRequest Updated WaitingListRequest, if any.
     */
    @Override
    @Transactional
    public void updatePatient(Long patientID, String name, String email, String password, String phNumber, String address, WaitingListRequest waitingListRequest)
    {
        Patient patient = patientRepository.findPatientById(patientID);

        if (patient != null) {
            if (name != null && !Objects.equals(patient.getName(), name)) patient.setName(name);
            if (email != null && !Objects.equals(patient.getEmail(), email)) patient.setEmail(email);
            if (password != null && !Objects.equals(patient.getPassword(), password)) patient.setPassword(password);
            if (phNumber != null && !Objects.equals(patient.getPhoneNumber(), phNumber)) patient.setPhoneNumber(phNumber);
            if (address != null && !Objects.equals(patient.getAddress(), address)) patient.setAddress(address);
            if (waitingListRequest != null && !Objects.equals(patient.getWaitingListRequest(), waitingListRequest))
            {
                WaitingListRequest wlr = waitingListService.getRequestByPatient(patient);
                patient.setWaitingListRequest(wlr);
            }
        }
    }
}
