package is.hi.hbv501g.hbv1.Persistence.Repositories;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long > {
    Patient save(Patient patient);
    void delete(Patient patient);
    
    Patient findByEmail(String email);
    List<Patient> findAll();


}
