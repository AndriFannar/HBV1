package is.hi.hbv501g.hbv1.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository< User, Long > {
    User save(User user);
    void delete(User user);
    
    Optional<User> findById(Long id);
    List<User> findAll();
}
