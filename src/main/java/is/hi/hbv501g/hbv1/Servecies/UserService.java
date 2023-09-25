package is.hi.hbv501g.hbv1.Servecies;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

public interface UserService {
    List<User> findAll();
    User signUp(User patient);
}
