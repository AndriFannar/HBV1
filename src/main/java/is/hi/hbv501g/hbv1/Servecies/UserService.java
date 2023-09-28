package is.hi.hbv501g.hbv1.Servecies;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

public interface UserService {
    List<User> findAll();
    User save(User user);
    void delete(User user);
    User findByEmail(String email);
    User login(User user);
    
    //List<Patient> findAllPatient();
    //List<Physiotherapist> findAllPhysio();

}
