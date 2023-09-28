package is.hi.hbv501g.hbv1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;

import is.hi.hbv501g.hbv1.Servecies.UserService;

@Controller
public class HomeController {
    
    private UserService userService;

    @Autowired
    public HomeController(UserService uS){
        this.userService = uS;
    }

    @RequestMapping("/")
    public String index(Model model){
        List<User> allUsers = userService.findAll();
        model.addAttribute("user", allUsers);
        return "index";
    }
    
}
