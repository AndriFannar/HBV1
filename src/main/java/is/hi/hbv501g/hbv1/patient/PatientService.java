package is.hi.hbv501g.hbv1.patient;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class PatientService
{
    public List<Patient> getPatients()
    {
        return List.of(
                new Patient(
                        "0502003480",
                        "Jón Jónsson",
                        LocalDate.of(2000, Month.FEBRUARY, 5),
                        "Jon@Jonsson.is"
                )
        );
    }
}
