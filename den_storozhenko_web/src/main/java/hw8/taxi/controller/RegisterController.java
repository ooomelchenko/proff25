package hw8.taxi.controller;

import hw8.taxi.exception.AuthorizationException;
import hw8.taxi.service.AuthorizationService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Controller
@SessionAttributes({"id","role"})
public class RegisterController {

    public static final Logger log = Logger.getLogger(AuthenticationController.class);
    @Autowired
    private AuthorizationService authorizationService;

    @PostConstruct
    public void init(){
        Locale.setDefault(Locale.ENGLISH);
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public
    String registerForm(Model model) {
        log.info("/register.html controller");
        return "register";
    }


    /*@RequestMapping(value = "/registersuccess", method = RequestMethod.POST)
    public
    String register(@RequestParam String login, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String id, Model model) {
        log.info("/registersuccess controller");
        try{
            if (!authorizationService.register(login,password,confirmPassword,id)){
                model.addAttribute("authorizationEx","Login "+login+" already exists. Enter other login.");
                return "register";
            }
            model.addAttribute("info","User "+login+" registration is successful.<br>");
            return "index";
        } catch (AuthorizationException authorizationException) {
            model.addAttribute("authorizationEx", authorizationException.getMessage());
            return "register";
        } catch (HibernateException e){
            model.addAttribute("error","Database error.");
            return "register";
        }
    }*/

    @RequestMapping(value = "/registerSuccess", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(@RequestParam String login, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String ident, Model model) {
        log.info("/registerSuccess controller");
        try{
            if (!authorizationService.register(login,password,confirmPassword,ident)){
                return "<font color=\"RED\">Login "+login+" already exists. Enter other login.<font>";
            }
            return "<font color=\"BLUE\">User "+login+" registration is successful.<font>";
        } catch (AuthorizationException authorizationException) {
            return "<font color=\"RED\">"+authorizationException.getMessage()+"<font>";
        } catch (HibernateException e){
            return "<font color=\"RED\">Database error.<font>";
        }
    }
}
