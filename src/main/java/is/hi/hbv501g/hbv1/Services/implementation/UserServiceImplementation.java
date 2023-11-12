package is.hi.hbv501g.hbv1.Services.Implementation;

import java.util.List;
import java.util.regex.Pattern;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hbv501g.hbv1.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv1.Services.UserService;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class implementation for User objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 2.0
 */
@Service
public class UserServiceImplementation implements UserService
{
    // Variables.
    private final UserRepository userRepository;


    /**
     * Constructs a new UserServiceImplementation.
     *
     * @param userRepository UserRepository linked to service.
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    /**
     * Find all User objects saved to database, if any.
     *
     * @return List of all User objects in database, if any.
     */
    @Override
    public List<User> findAll()
    {
        return userRepository.findAll();
    }


    /**
     * Save a new User object to database.
     *
     * @param user User object to save.
     * @return     Saved User object.
     */
    @Override
    public User save(User user)
    {
        return userRepository.save(user);
    }


    /**
     * Delete a User object from database by ID.
     *
     * @param userID Unique ID of the User object to delete.
     */
    @Override
    public void delete(Long userID)
    {
        userRepository.deleteById(userID);
    }


    /**
     * Log in given User object.
     *
     * @param user User to log in.
     * @return     Logged in User.
     */
    @Override
    public User login(User user)
    {
        User doesExist = findByEmail(user.getEmail());
        if(doesExist != null)
        {
            if(doesExist.getPassword().equals(user.getPassword()))
            {
                return doesExist;
            }
        }

        return null;
    }


    /**
     * Find User object by e-mail.
     *
     * @param email E-mail of User object to find.
     * @return      User object with matching e-mail, if any.
     */
    @Override
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }


    /**
     * Find a User object by unique ID.
     *
     * @param userID Unique ID of User object to find.
     * @return       User with corresponding ID, if any.
     */
    public User findByID(Long userID)
    {
        return userRepository.findUserById(userID);
    }


    /**
     * Update User.
     *
     * @param userID      ID of the User to update.
     * @param updatedUser User with updated info.
     */
    @Transactional
    public void updateUser(Long userID, User updatedUser)
    {
        User user = userRepository.findUserById(userID);

        if (user != null)
        {
            if (updatedUser.getName()           != null) user.setName(updatedUser.getName());
            if (updatedUser.getEmail()          != null) user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword()       != null) user.setPassword(updatedUser.getPassword());
            if (updatedUser.getSsn()            != null) user.setSsn(updatedUser.getSsn());
            if (updatedUser.getPhoneNumber()    != null) user.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getAddress()        != null) user.setAddress(updatedUser.getAddress());
            if (updatedUser.getSpecialization() != null) user.setSpecialization(updatedUser.getSpecialization());
            if (updatedUser.getDescription()    != null) user.setDescription(updatedUser.getDescription());
        }
    }


    /**
     * Change role of User.
     *
     * @param userID            ID of the User to update.
     * @param isStaff           Is User a staff member.
     * @param isPhysiotherapist Is User a physiotherapist.
     * @param isAdmin           Is User an admin.
     */
    @Transactional
    public void changeRole(Long userID, boolean isStaff, boolean isPhysiotherapist, boolean isAdmin)
    {
        User user = userRepository.findUserById(userID);

        if (user != null)
        {
            if(isAdmin)
            {
                user.setAdmin(true);
                user.setStaff(true);
            }
            else if(isPhysiotherapist)
            {
                user.setPhysiotherapist(true);
                user.setStaff(true);
            }
            else if(isStaff)
            {
                user.setStaff(true);
            }
            else
            {
                user.setStaff(false);
                user.setPhysiotherapist(false);
                user.setAdmin(false);
            }
        }
    }


    /**
     * Find User object by SSN.
     *
     * @param ssn SSN of User to find.
     * @return    User with corresponding SSN, if any.
     */
    @Override
    public User findBySsn(String ssn)
    {
        return userRepository.findBySsn(ssn);
    }

    /**
     * Checks if SSN is valid.
     *
     * @param user User that is trying to sign up
     * @return String with error message if SSN is invalid
     */
    @Override
    public String validateSsn(User user) {
        String message = "";
        String kennitala = user.getSsn();
        User exists = findBySsn(kennitala);
        if(kennitala.isEmpty()){
            message += "Vantar að setja inn lykilorð";
        }
        else if(kennitala.length() != 10){
            message += "Kennitala ekki nógu löng.";
        } else if(!checkSsn(kennitala)){
            message += "Kennitala ólögleg.";
        } else if(exists != null){
            message += "Notandi nú þegar til með þessa kennitölu";
        }
        
        return message;
    }


    /**
     * Check if SSN is valid.
     *
     * @param ssn SSN to check.
     * @return    Boolean if SSN is valid.
     */
    private boolean checkSsn(String ssn){
        try {  
            int sum = 0;
            String[] stringKenni = ssn.split("");
            int[] kennitolur = new int[10];
            int[] margfeldisTala = {3, 2, 7, 6, 5, 4, 3, 2};

            for(int i = 0; i < kennitolur.length; i++){
                kennitolur[i] = Integer.parseInt(stringKenni[i]);
            }

            for(int i = 0; i < margfeldisTala.length; i++){
                sum += kennitolur[i]*margfeldisTala[i];
            }

            int mod = sum % 11;
            int magicNumber = 0;
            if (mod != 0) {
                magicNumber = 11 - mod;
            }

            return magicNumber == kennitolur[8];
        } catch(NumberFormatException e){  
            return false;  
        } 
    }

    /**
     * Checks if password is valid
     *
     * @param user User that is trying to sign up
     * @return String with error message if password is invalid
     */
    @Override
    public String validatePassword(User user){
        String message = "";
        String password = user.getPassword();
        if(password.isEmpty()){
            message += "Vantar lykilorð";
        } else {
            message = strengthOfPassword(password);
        }
        
        return message;
    }

    /**
     * Checks the strength of a password.
     *
     * @param password Password to check.
     * @return         String with error message, if any.
     */
    private String strengthOfPassword(String password){
        boolean hasLetter = false;
        boolean hasDigit = false;
        String message = "";

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {
                    hasLetter = true;
                } else if (Character.isDigit(x)) {
                    hasDigit = true;
                }

                if (hasLetter && hasDigit) {
                    break;
                }
            }
            if (hasLetter && hasDigit) {
                message = "";
            } else if(hasLetter || hasDigit){
                message += "Lykilorð verður að innihalda bókstafi og tölur";
            }
        } else {
            message += "Lykilorð verður að vera 8 stafir/tölur eða meira";
        }
        return message;
    }


    /**
     * Checks if e-mail is valid.
     *
     * @param user User that is trying to sign up.
     * @return String with error message if e-mail is invalid
     */
    @Override
    public String validateEmail(User user)
    {
        String message = "";
        String email = user.getEmail();
        User exists = findByEmail(email);
        if(exists != null){
            message += "Notandi þegar til";
        } else if(email.isEmpty()){
            message += "Vantar netfang";
        } else if(!validEmail(email)){
            message += "Netfang ekki netfang";
        }
        return message;
    }


    /**
     * Checks if e-mail is valid.
     *
     * @param email E-mail to check.
     * @return      Boolean if the e-mail is valid.
     */
    private boolean validEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        return pat.matcher(email).matches();
    }

    /**
     * Checks if phone number is valid.
     *
     * @param user User that is trying to sign up.
     * @return String with error message if phone number is invalid
     */
    @Override
    public String validatePhoneNumber(User user)
    {
        try
        {
            String message = "";
            String phNumber = user.getPhoneNumber();
            if(phNumber.isEmpty()){
                message += "Vantar símanúmer";
            } 
            int numbers = Integer.parseInt(phNumber);
            if(Integer.toString(numbers).length() != 7){
                message += "Símanúmer ekki 7 stafir";
            }
            return message;
        } catch(NumberFormatException e){  
            return "Símanúmer ólöglegt" ;  
        }  
    }

    /**
     * Finds User by isPhysiotherapist.
     *
     * @param physiotherapist Search for physiotherapists.
     * @return                List of User objects with matching role, if any.
     */
    public List<User> findByIsPhysiotherapist(boolean physiotherapist)
    {
        return userRepository.findUserByIsPhysiotherapist(physiotherapist);
    }
}
