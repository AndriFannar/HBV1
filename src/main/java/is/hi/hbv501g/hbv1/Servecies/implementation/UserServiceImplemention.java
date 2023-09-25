package is.hi.hbv501g.hbv1.Servecies.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.UserService;

@Service
public class UserServiceImplemention implements UserService{

    private List<User> userRespotory = new ArrayList<>();
    private int id_counter = 0;

    @Autowired
    public UserServiceImplemention(){
        userRespotory.add(new User("Jón Jónssons", "Jon@Jonsson.is", "Password123", "1234567890", "2222222", false));


        for(User u: userRespotory){
            u.setId((long) id_counter);
            id_counter++;
        }
    }

    @Override
    public User findByName(String name) {
        for(User u: userRespotory){
            if(u.getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRespotory;
    }

    @Override
    public User findById(long ID) {
        for(User u: userRespotory){
            if(u.getId() == ID){
                return u;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        user.setId((long) id_counter);
        id_counter++;
        userRespotory.add(user);
        return user;
    }

    @Override
    public void delete(User user) {
        userRespotory.remove(user);
    }
    
}
