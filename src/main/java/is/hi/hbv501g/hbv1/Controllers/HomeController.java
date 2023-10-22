package is.hi.hbv501g.hbv1.Controllers;

import java.util.List;

import is.hi.hbv501g.hbv1.Persistence.Entities.Patient;
import is.hi.hbv501g.hbv1.Persistence.Entities.User;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Services.WaitingListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import is.hi.hbv501g.hbv1.Services.UserService;


/**
 * Controller for front page.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-25
 * @version 1.0
 */
@Controller
public class HomeController
{
    // Variables.
    private UserService userService;

    private WaitingListService waitingListService; // Is accessing other Services ok?


    /**
     * Construct a new WaitingListController.
     *
     * @param wS WaitingListService linked to controller.
     */
    @Autowired
    public HomeController(UserService uS, WaitingListService wS)
    {
        this.userService = uS;
        this.waitingListService = wS;
    }


    /**
     * Load in front page.
     *
     * @param model   Model for page.
     * @param session Current HttpSession.
     * @return        Front page.
     */
    @RequestMapping("/index")
    public String index(Model model, HttpSession session){
        List<User> allPatients = userService.findAll();
        model.addAttribute("user", allPatients);

        User patient = (User) session.getAttribute("LoggedInUser");
        model.addAttribute("login", patient);

        if (patient != null)
        {
            WaitingListRequest waitingListRequest = patient.getWaitingListRequest();
            if (waitingListRequest != null)
            {
                model.addAttribute("request", waitingListRequest);
            }
        }

        return "index";
    }
}
