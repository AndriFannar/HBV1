package is.hi.hbv501g.hbv1;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@Configuration
public class DatabaseConfig
{
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
      return args -> {
          User jonJonsson = new User(
                  "Jón Jónsson",
                  "Jon@Jonsson.is",
                  "admin",
                  "1234567890",
                  "1234567",
                  "Hí",
                  true,
                  true,
                  true,
                  "Ekkert",
                  "Ekkert"
          );

          List<User> users = repository.findUserByIsPhysiotherapist(true);

          if (users.isEmpty())
          {
              repository.saveAll(List.of(jonJonsson));
          }
          else
          {
              for(User user : users)
              {
                  if((Objects.equals(user.getEmail(), "Jon@Jonsson.is")) && users.size() > 1) repository.deleteById(user.getId());
              }
          }
      };
    };
}
