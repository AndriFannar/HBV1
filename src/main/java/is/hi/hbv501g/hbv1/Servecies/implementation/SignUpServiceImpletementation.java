package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.SignUpService;

@Service
public class SignUpServiceImpletementation implements SignUpService{

    private List<User> userRespotory = new ArrayList<>();
    private int id_counter = 0;
    
    @Override
    public User signUp(User user) {
        user.setId((long) id_counter);
        id_counter++;
        userRespotory.add(user);
        return user;
    }
}
