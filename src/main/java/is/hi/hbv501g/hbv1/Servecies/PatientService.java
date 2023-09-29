package is.hi.hbv501g.hbv1.Servecies;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.User;

public interface PatientService {
    List<Patient> findAll();
    Patient save(Patient patient);
    void delete(Long ID);
    Patient findByEmail(String email);
    Patient login(Patient patient);
}
