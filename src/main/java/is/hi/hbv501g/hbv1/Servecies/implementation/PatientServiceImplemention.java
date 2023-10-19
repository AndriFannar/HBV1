package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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


    /**
     * Constructs a new PatientServiceImplementation.
     *
     * @param patientRepository PatientRepository linked to service.
     */
    @Autowired
    public PatientServiceImplemention(PatientRepository patientRepository)
    {
        this.patientRepository = patientRepository;
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
     * @param patientID           Unique ID of Staff object to update.
     * @param name              Updated name, if any.
     * @param email             Updated e-mail, if any.
     * @param password          Updated password, if any.
     * @param phNumber          Updated phone number, if any.
     * @param address           Updated address, if any.
     */
    @Override
    @Transactional
    public void updatePatient(Long patientID, String name, String email, String password, String phNumber, String address)
    {
        Patient patient = patientRepository.findPatientById(patientID);

        if (patient != null) {
            if (name != null && !Objects.equals(patient.getName(), name)) patient.setName(name);
            if (email != null && !Objects.equals(patient.getEmail(), email)) patient.setEmail(email);
            if (password != null && !Objects.equals(patient.getPassword(), password)) patient.setPassword(password);
            if (phNumber != null && !Objects.equals(patient.getPhoneNumber(), phNumber)) patient.setPhoneNumber(phNumber);
            if (address != null && !Objects.equals(patient.getAddress(), address)) patient.setAddress(address);
        }
    }

    @Override
    public Patient findByKennitala(String kennitala){
        return patientRepository.findByKennitala(kennitala);
    }

    @Override
    public String validateKennitala(Patient patient) {
        String message = "";
        String kennitala = patient.getKennitala();
        Patient exists = findByKennitala(kennitala);
        if(kennitala.length() == 0){
            message += "Vantar að setja inn lykilorð";
        }
        else if(kennitala.length() != 10){
            message += "Kennitala ekki nógu löng.";
        } else if(!checkKennitala(kennitala)){
            message += "Kennitala ólögleg.";
        } else if(exists != null){
            message += "Notandi nú þegar til með þessa kennitölu";
        }
        
        return message;
    }

    private boolean checkKennitala(String kennitala){
        try {  
            int sum = 0;
            String[] stringKenni = kennitala.split("");
            int[] kennitolur = new int[10];
            int[] margfeldisTala = {3, 2, 7, 6, 5, 4, 3, 2};

            for(int i = 0; i < kennitolur.length; i++){
                kennitolur[i] = Integer.parseInt(stringKenni[i]);
            }

            for(int i = 0; i < margfeldisTala.length; i++){
                sum += kennitolur[i]*margfeldisTala[i];
            }

            int mod = sum % 11;
            int magicNumber = 0;
            if (mod != 0) {
                magicNumber = 11 - mod;
            }

            return magicNumber == kennitolur[8];
        } catch(NumberFormatException e){  
            return false;  
        } 
    }

    @Override
    public String validatePassword(Patient patient){
        String message = "";
        String password = patient.getPassword();
        if(password.length() == 0){
            message += "Vantar lykilorð";
        } else {
            message = strengthOfPassword(password);
        }
        
        return message;
    }

    private String strengthOfPassword(String password){
        boolean hasLetter = false;
        boolean hasDigit = false;
        String message = "";

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {
                    hasLetter = true;
                } else if (Character.isDigit(x)) {
                    hasDigit = true;
                }

                if (hasLetter && hasDigit) {
                    break;
                }
            }
            if (hasLetter && hasDigit) {
                message = "";
            } else if(hasLetter || hasDigit){
                message += "Lykilorð verður að innihalda bókstafi og tölur";
            }
        } else {
            message += "Lykilorð verður að vera 8 stafir/tölur eða meira";
        }
        return message;
    }

    @Override
    public String validateEmail(Patient patient){
        String message = "";
        String email = patient.getEmail();
        Patient exists = findByEmail(email);
        if(exists != null){
            message += "Notandi þegar til";
        } else if(email.length() == 0){
            message += "Vantar netfang";
        } else if(!validEmail(email)){
            message += "Netfang ekki netfang";
        }
        return message;
    }

    private boolean validEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        return pat.matcher(email).matches();
    }

    public String validatePhoneNumber(Patient patient){
        try{
            String message = "";
            String phNumber = patient.getPhoneNumber();
            if(phNumber.length() == 0){
                message += "Vantar símanúmer";
            } 
            int numbers = Integer.parseInt(phNumber);
            if(Integer.toString(numbers).length() != 7){
                message += "Símanúmer ekki 7 stafir";
            }
            return message;
        } catch(NumberFormatException e){  
            return "Símanúmer ólöglegt" ;  
        }  
    }
}
