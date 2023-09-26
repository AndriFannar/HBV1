package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv1.Servecies.SignUpService;

@Service
public class SignUpServiceImpletementation implements SignUpService{

    private UserRepository userRepository;

    @Autowired
    public SignUpServiceImpletementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
    @Override
    public User signUp(User user) {
        return userRepository.save(user);
    }
}
