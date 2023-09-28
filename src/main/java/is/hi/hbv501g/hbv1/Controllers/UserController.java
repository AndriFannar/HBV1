package is.hi.hbv501g.hbv1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.UserService;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService uS){
        this.userService = uS;
    }

    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(User user, Model model){
        return "newUser";
    }

    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(User user, BindingResult result,  Model model, HttpSession session){
        if(result.hasErrors()){
            return "redirect:/signUp";
        }
        User exists = userService.findByEmail(user.getEmail());
        if(exists == null){
            userService.save(user);
            
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "redirect:/loggedin";
        }
        return "redirect:/";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginPost(User user, Model model){
        return "login";
    }

    @RequestMapping(value="/loggedin", method=RequestMethod.GET)
    public String loggedInGET(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser != null){
            model.addAttribute("LoggedInUser", sessionUser);
            return "LoggedInUser";
        }
        return "redirect:/";
    }


    
}
