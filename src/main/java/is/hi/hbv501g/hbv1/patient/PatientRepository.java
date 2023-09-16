package is.hi.hbv501g.hbv1.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>
{
    // @Query("SELECT p FROM Patient p WHERE p.email = ?1")
    Optional<Patient> findPatientByEmail(String email);
}
