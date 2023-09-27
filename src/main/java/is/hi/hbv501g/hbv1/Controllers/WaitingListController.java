package is.hi.hbv501g.hbv1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Servecies.UserService;


@Controller
public class WaitingListController {

    private UserService userService;

    @Autowired
    public WaitingListController(UserService uS){
        this.userService = uS;
    }


    @RequestMapping(value="/createRequest", method = RequestMethod.GET)
    public String createRequest(User user, Model model){
        return "createRequest";
    }




    
}
