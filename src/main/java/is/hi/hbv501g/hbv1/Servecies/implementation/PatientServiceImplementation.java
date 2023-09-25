package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.time.Month.FEBRUARY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Servecies.PatientService;

@Service
public class PatientServiceImplementation implements PatientService {

    private List<Patient> patientReposity = new ArrayList<>();
    private int id_counter = 0;

    @Autowired
    public PatientServiceImplementation(){
        patientReposity.add(new Patient("Jón Jónssons", LocalDate.of(2000, FEBRUARY, 5), "Jon@Jonsson.is"));
        patientReposity.add(new Patient("Jóna Jónssons", LocalDate.of(2001, FEBRUARY, 5), "Jona@Jonsson.is"));

        for(Patient p: patientReposity){
            p.setId((long) id_counter);
            id_counter++;
        }
    }

    @Override
    public Patient findByName(String name) {
        for(Patient p: patientReposity){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        System.out.println(patientReposity);
        return patientReposity;
    }

    @Override
    public Patient findById(long ID) {
        for(Patient p: patientReposity){
            if(p.getId() == ID){
                return p;
            }
        }
        return null;
    }

    @Override
    public Patient save(Patient patient) {
        patient.setId((long) id_counter);
        id_counter++;
        patientReposity.add(patient);
        return patient;

    }

    @Override
    public void delete(Patient patient) {
        patientReposity.remove(patient);
    }
    
}
