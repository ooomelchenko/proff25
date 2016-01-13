package nadrabank.controller;

import nadrabank.domain.Client;
import nadrabank.domain.Credit;
import nadrabank.domain.User;
import nadrabank.domain.Lot;
import nadrabank.service.ClientService;
import nadrabank.service.UserService;
import nadrabank.service.CreditService;
import nadrabank.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionAttributes("userId")
public class CreditController {
    @Autowired
    private CreditService creditService;
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    private boolean isAuth(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return false;
        }
        return true;
    }
/*    private User getCurrUser(HttpSession session){
        session.getAttribute("userId");
        return userService.getUser(0l);
    }*/

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String main(HttpSession session) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            return "Menu";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private
    @ResponseBody
    String logCheck(@RequestParam("login") String login, @RequestParam("password") String password, HttpSession session, Model uID) {
        if (userService.isExist(login, password)) {
            uID.addAttribute("userId", login);
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "/lots", method = RequestMethod.GET)
    private
    @ResponseBody
    List<String> jsonGetLots() {
        List<String> strLots = new ArrayList<>();
        List<Lot> lots = lotService.getLots();
        strLots.add("№ Лоту" + '|' + "Регіон" + '|' + "Компанія" + '|' + "Стадія роботи" + '|' + "Співробітник" + '|' + "Коментар" + '|' + "Дата створення лоту" + '|' + "Кіль-ть кредитів" + "|" + "Загальний борг, грн.");
        for (Lot lot : lots) {
            Formatter f = new Formatter();
            Long count = lotService.lotCount(lot);
            Double zb = lotService.lotSum(lot);
            strLots.add(lot.getId().toString() + '|' + lot.toString() + '|' + count.toString() + '|' + f.format("%,.0f", zb).toString());
        }
        return strLots;
    }

    @RequestMapping(value = "/lotRedact", method = RequestMethod.GET)
    private String toLotRedactor(/*@RequestParam("lotID") String lotId*/) {
        /*Lot lot = lotService.getLot(Long.parseLong(lotId));*/
        return "LotRedactor";
    }


    @RequestMapping(value = "/lotDel", method = RequestMethod.POST)
    private
    @ResponseBody
    String deleteLot(@RequestParam("lotID") String lotId) {
        lotService.delLot(Long.parseLong(lotId));
        return "1";
    }

    @RequestMapping(value = "/lotForm", method = RequestMethod.GET)
    private String formLot(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            model.addAttribute("clList", clientService.showClient());
            return "Creator";
        }
    }

    @RequestMapping(value = "/selectedCRD", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> selectCrd(
            @RequestParam("types") String types,
            @RequestParam("regions") String regs,
            @RequestParam("curs") String curs,
            @RequestParam("dpdmin") int dpdmin,
            @RequestParam("dpdmax") int dpdmax,
            @RequestParam("zbmin") double zbmin,
            @RequestParam("zbmax") double zbmax) {
        String[] typesMass = types.split(",");
        String[] regsMass = regs.split(",");
        String[] curMass = curs.split(",");
        List<Credit> crList = creditService.selectCredits(typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        List<String> rezList = new ArrayList<>();
        rezList.add("ID_Bars" + '|' + "ID_Loans" + '|' + "ІНН" + '|' + "Номер договору" + '|' + "ФІО" + '|' + "Регіон" + '|' + "Тип кредиту" + '|' + "Маркер" + '|' + "Валюта" + '|'
                + "Загальний борг, грн." + '|' + "dpd" + '|' + "Дата видачі" + '|' + "Дата закінчення" + '|' + "Опис застави" + '|' + "Вартість застави, грн.");
        for (Credit cr : crList) {
            rezList.add(cr.getId() + cr.toShotString());
        }
        return rezList;
    }

    @RequestMapping(value = "/selectedParam", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getParam(@RequestParam("types") String types,
                          @RequestParam("regions") String regs,
                          @RequestParam("curs") String curs,
                          @RequestParam("dpdmin") int dpdmin,
                          @RequestParam("dpdmax") int dpdmax,
                          @RequestParam("zbmin") double zbmin,
                          @RequestParam("zbmax") double zbmax) {
        String[] typesMass = types.split(",");
        String[] regsMass = regs.split(",");
        String[] curMass = curs.split(",");
        List<String> paramList = creditService.getCreditsResults(typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        return paramList;
    }

    @RequestMapping(value = "/createLot", method = RequestMethod.GET)
    private
    @ResponseBody
    String createLot(HttpSession session,
                     @RequestParam("companyId") String companyId, @RequestParam("comment") String comment,
                     @RequestParam("types") String types, @RequestParam("regions") String regs, @RequestParam("curs") String curs,
                     @RequestParam("dpdmin") int dpdmin, @RequestParam("dpdmax") int dpdmax,
                     @RequestParam("zbmin") double zbmin, @RequestParam("zbmax") double zbmax) {

        String[] typesMass = types.split(",");
        String[] regsMass = regs.split(",");
        String[] curMass = curs.split(",");
        String userLogin = (String) session.getAttribute("userId");
        User user = userService.getByLogin(userLogin);
        Client client = clientService.getClient(Long.parseLong(companyId));

        Lot newlot = new Lot(comment, user, client);
        Long idL = lotService.createLot(newlot);

/*        List<Credit> crList = creditService.selectCredits(typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        for(Credit crdt: crList){
            crdt.setLot(newlot);
            creditService.updateCredit(crdt);
        }*/
        boolean isitAdded = creditService.addCreditsToLot(newlot, typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        if (isitAdded)
            return "1";
        else return "0";
    }

    @RequestMapping(value = "/statusChanger", method = RequestMethod.POST)
    private
    @ResponseBody
    String changeStatus
            (@RequestParam("lotID") String lotId, @RequestParam("status") String status) {
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        lot.setWorkStage(status);
        lotService.updateLot(lot);
        return "1";
    }

    @RequestMapping(value = "/regions", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllRegs() {
        List<String> regList;
        regList = creditService.getRegions();
        return regList;
    }

    @RequestMapping(value = "/crType", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllTypes() {
        List<String> typesList;
        typesList = creditService.getTypes();
        return typesList;
    }

    @RequestMapping(value = "/getCurs", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllCurr() {
        List<String> currList;
        currList = creditService.getCurrencys();
        return currList;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.HEAD})
    private void creator() {

        User empl1 = new User("Київ", "Омельченко Олександр Олександрович", "mer", "a44n73", "admin");
        User empl2 = new User("Київ", "Опанасенко Вячеслав Володимирович", "slava", "111", "manager");
        User empl3 = new User("Київ", "Шніткова Світлана Василівна", "sv", "222", "user");
        Client client1 = new Client("0123456789", "DolgOff", "м.Київ, вул. Січових Стрільців, 17", "02165 м.Київ, вул. Лисківська, 50", "Петров Віктор Михайлович", "14056", "dolg@gmail.com");
        Client client2 = new Client("1123456789", "Bezdolgov", "м.Київ, вул. Лисківська, 50", "02165 м.Київ, вул. Січових Стрільців, 10", "Сидоров Віктор Михайлович", "20-453", "dolg@ukr.net");
        Client client3 = new Client("2123456789", "Collector", "м.Київ, вул. Січових Стрільців, 15", "02165 м.Київ, вул. Січових Стрільців, 10", "Іванов Віктор Михайлович", "20-441", "coll@gmail.com");
        Client client4 = new Client("3123456789", "UKG", "м.Київ, вул. Ломоносова, 11б", "02165 м.Київ, вул. Січових Стрільців, 10", "Ярош Дмитро Михайлович", "20-551", "ukg@gmail.com");
        userService.createUser(empl1);
        userService.createUser(empl2);
        userService.createUser(empl3);
        clientService.createClient(client1);
        clientService.createClient(client2);
        clientService.createClient(client3);
        clientService.createClient(client4);
        /*Lot lot1 = new Lot("Київ","Проверка лота", "комментарии", userService.getUser(1L), clientService.getClient(1L));
        Lot lot2 = new Lot("Київ","Винесення на МКУА", "комментарии", userService.getUser(2L), clientService.getClient(2L));
        Lot lot3 = new Lot("Київ","Проверка лота", "комментарии", userService.getUser(3L), clientService.getClient(3L));
        Lot lot4 = new Lot("Київ","Винесення на МКУА", "комментарии", userService.getUser(1L), clientService.getClient(4L));*/
      /*Set <Credit> credits = new HashSet<>();
        Credit cr1= creditService.getCredit(220931l);
        Credit cr2= creditService.getCredit(220940l);
        credits.add(cr1);
        credits.add(cr2);
        lot1.setCredits(credits);*/
        /*lotService.createLot(lot1);
        lotService.createLot(lot2);
        lotService.createLot(lot3);
        lotService.createLot(lot4);*/
/*
        credit1.setLot(lotService.getLot(1l));
        credit2.setLot(lotService.getLot(2l));
        credit3.setLot(lotService.getLot(3l));
        credit4.setLot(lotService.getLot(4l));
        credit5.setLot(lotService.getLot(1l));
*/
    }
}