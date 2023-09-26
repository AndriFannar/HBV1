package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv1.Servecies.UserService;

@Service
public class UserServiceImplemention implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplemention(UserRepository userRespotory){
        this.userRepository = userRespotory;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
}
