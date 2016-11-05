package nadrabank.controller;

import nadrabank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"userId"})
public class BimController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String main(HttpSession session) {
            return "Menu";
    }
    @RequestMapping(value = "contacts", method = {RequestMethod.GET})
    private String getContacts() {
        return "Contacts";
    }
    @RequestMapping(value = "gallery", method = {RequestMethod.GET})
    private String gallery() {
        return "Gallery";
    }
    @RequestMapping(value = "about", method = {RequestMethod.GET})
    private String about() {
        return "About";
    }
}