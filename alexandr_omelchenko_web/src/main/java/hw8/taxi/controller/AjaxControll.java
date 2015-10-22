package hw8.taxi.controller;

import hw8.taxi.domain.Client;
import hw8.taxi.domain.Operator;
import hw8.taxi.service.AuthenticationServiceImpl;
import hw8.taxi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("id")
public class AjaxControll {
    @Autowired
    private ClientService service;
    private AuthenticationServiceImpl authService;
    @RequestMapping(value = "/ajax", method = RequestMethod.POST) public
    @ResponseBody String ajax(@RequestParam("login") String login, @RequestParam("password") String password, Model model) {
        if(!login.equals(password)){
            Client client1 = service.getClient(2L);
            Client client2 = service.getClient(4L);
            String result = client1.toString()+client2.toString();
            return result;
        }
        else {
            return "0";
        }
    }
    @RequestMapping(value = "/ajax1", method = RequestMethod.POST) public
    @ResponseBody String ajax1(@RequestParam("login") String login, @RequestParam("password") String password) {
        if(login.equals("mer")&&password.equals("111") ){//
            return "YES";
        }
        else {
            return "NO";
        }
    }
    @RequestMapping(value = "/ajax2", method = RequestMethod.POST) public
    @ResponseBody String newajax(Model model) {
        Client client1 = service.getClient(2L);
        Client client2 = service.getClient(4L);
        List<Client> clients = new ArrayList<>() ;
        clients.add(client1);
        clients.add(client2);

        if (clients.size() == 0){
            return "Table of clients is empty";
        }
        else {
            String result="";
            for(Client cl : clients){
                result = result + cl.getFirstname()+"|";
            }
            result=result.substring(0, result.length()-1);
            return result;
        }
    }
    @RequestMapping(value = "/json1",method = RequestMethod.POST)
    public @ResponseBody List<Operator> jsonMethod(@RequestParam("login") String login, @RequestParam("password") String password){
        if (login.equals("mer")&&password.equals("111")){
            List<Operator> operators = new ArrayList<>();
            operators.add(new Operator("mer", "cop"));
            operators.add(new Operator("barry", "parol"));
            return operators;
        }
        else{
            return null;
        }
    }
    /*@RequestMapping(value = "/json1",method = RequestMethod.POST)
    public @ResponseBody List <Client> jsonMethod(@RequestParam("login") String login, @RequestParam("password") String password){
        if (login.equals("mer")&&password.equals("111")){
            Client client1 = service.getClient(2L);
            Client client2 = service.getClient(4L);
            List<Client> clients = new ArrayList<>();
            clients.add(client1);
            clients.add(client2);
            return clients;
        }
        else{
            return null;
        }
    }*/
    }