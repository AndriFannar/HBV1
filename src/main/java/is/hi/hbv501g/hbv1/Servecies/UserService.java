package is.hi.hbv501g.hbv1.Servecies;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

public interface UserService {
    User findByName(String name);
    List<User> findAll();
    User findById(long ID);
    User save(User patient);
    void delete(User patient);
}
