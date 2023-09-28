package is.hi.hbv501g.hbv1.Servecies;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;

public interface PatientService {
    Patient findByName(String name);
    List<Patient> findAll();
    Patient findById(long ID);
    Patient save(Patient patient);
    void delete(Patient patient);
}
