package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Repositories.PatientRepository;
import is.hi.hbv501g.hbv1.Servecies.PatientService;

@Service
public class PatientServiceImplemention implements PatientService
{

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImplemention(PatientRepository patientRespotory){
        this.patientRepository = patientRespotory;
    }

    @Override
    public List<Patient> findAll() {
        return (List<Patient>)(List<?>)patientRepository.findAll();
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    @Override
    public Patient login(Patient patient) {
        Patient doesExist = findByEmail(patient.getEmail());
        if(doesExist != null){
            if(doesExist.getPassword().equals(patient.getPassword())){
                return doesExist;
            }
        }
        return null;
    }

    @Override
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
}
