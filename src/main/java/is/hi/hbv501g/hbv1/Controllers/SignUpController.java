package is.hi.hbv501g.hbv1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.SignUpService;

@Controller
public class SignUpController {

    private SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService uS){
        this.signUpService = uS;
    }

    @RequestMapping(value="/signUp", method = RequestMethod.GET)
    public String signUpForm(User user, Model model){
        return "newUser";
    }

    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "newUser";
        }
        signUpService.signUp(user);
        return "redirect:/";


    }
}
