package is.hi.hbv501g.hbv1.patient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class PatientConfig
{
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository repository) {
      return args -> {
          Patient jonJonsson = new Patient(
                  "Jón Jónsson",
                  LocalDate.of(2000, FEBRUARY, 5),
                  "Jon@Jonsson.is"
          );

          Patient jonaJonudottir = new Patient(
                  "Jóna Jónudóttir",
                  LocalDate.of(1990, FEBRUARY, 5),
                  "Jona@Jonasdottir.is"
          );

          repository.saveAll(List.of(jonJonsson, jonaJonudottir));
      };
    };
}
