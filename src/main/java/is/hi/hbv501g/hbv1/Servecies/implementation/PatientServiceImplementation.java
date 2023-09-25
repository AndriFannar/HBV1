package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Servecies.PatientService;

public class PatientServiceImplementation implements PatientService {

    private static final Month FEBRUARY = null;
    private List<Patient> patientReposity = new ArrayList<>();
    private int id_counter = 0;

    @Autowired
    public PatientServiceImplementation(){
        patientReposity.add(new Patient("Jón Jónssons", LocalDate.of(2000, FEBRUARY, 5), "Jon@Jonsson.is"));
        patientReposity.add(new Patient("Jóna Jónssons", LocalDate.of(2001, FEBRUARY, 5), "Jona@Jonsson.is"));
    }

    @Override
    public Patient findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    @Override
    public List<Patient> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Patient findById(long ID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Patient save(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void delete(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
