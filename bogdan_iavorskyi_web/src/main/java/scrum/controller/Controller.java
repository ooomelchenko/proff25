package scrum.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import scrum.domain.Taxi;
import scrum.domain.User;
import scrum.service.TaxiService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by oleg on 14.07.15.
 */@org.springframework.stereotype.Controller
   @SessionAttributes("id")
public class Controller {

    @Autowired
    private TaxiService service;
    public static final Logger log = Logger.getLogger(Controller.class);

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model) {
        log.info("/index controller");
        model.addAttribute("name", "default");
        return "index";
    }


    @RequestMapping(value = "/request.html", method = RequestMethod.POST)
    public String medium(@RequestParam("login") String login, @RequestParam("password") String password, Model model) {
        log.info("/request.html controller");

        if (authenticate(login,password, service.getUserList())) {

            List<Taxi> taxists = service.getTaxiList();
            model.addAttribute("taxists", taxists);
            System.out.println(taxists.size());
            return "dashboard";
        }
        else {
            model.addAttribute("message", "несуществующий аккаунт или неверный пароль, попробуйте еще разок");
            return "dashboard";
        }
    }

    private static boolean authenticate(String login, String password, List<User> users){
        System.out.println(users.size());
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                if (user.getPass().equals(password)) {
                    return true;
                }
            }
        }
            return false;
    }

    private void fulfill(){
    User user1 = new User("log", "pass");
        User user2 = new User("aaa", "1111");
        User user3 = new User("name", "trouble");
        User user4 = new User("azaza", "ajajaja");
        User user5 = new User("whosYourDaddy?", "ImyaDaddy");
        service.createUser(user1);
        service.createUser(user2);
        service.createUser(user3);
        service.createUser(user4);
        service.createUser(user5);
        Taxi taxi1 = new Taxi("vasya", "", "", "");
        Taxi taxi2 = new Taxi("petya", "", "", "");
        Taxi taxi3 = new Taxi("kolya", "", "", "");
        Taxi taxi4 = new Taxi("andrey", "", "", "");
        Taxi taxi5 = new Taxi("", "", "", "");
    }

    
}

