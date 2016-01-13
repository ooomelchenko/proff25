package nadrabank.controller;
import nadrabank.domain.Client;
import nadrabank.domain.Employee;
import nadrabank.domain.Equipment;
import nadrabank.domain.Lot;
import nadrabank.service.ClientService;
import nadrabank.service.EmployeeService;
import nadrabank.service.EquipmentService;
import nadrabank.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@SessionAttributes("userID")
public class NadraController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private LotService lotService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    private boolean isAuth(HttpSession session) {
        if (session.getAttribute("userID") == null) {
            return false;
        }
        return true;
    }
    private Employee getCurrEmployee(HttpSession session){
        session.getAttribute("userID");
        return employeeService.getEmployee(0l);
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String main(HttpSession session) {
        if (!isAuth(session)){
            return "logPage";}
        else{
        return "Menu";}
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody String logCheck(@RequestParam("login") String login, @RequestParam("password") String password, HttpSession session, Model uID){
        if (employeeService.isExist(login, password)){
            uID.addAttribute("userID", login);
            return "1";
        }
        else{ return "0"; }
    }
@RequestMapping(value = "/lots", method = RequestMethod.GET)
public @ResponseBody List<String> jsonGetLots (){
    List<String> strLots =new ArrayList<>();
    List <Lot> lots=lotService.showLot();
    strLots.add("№ Лоту"+'|'+"Дата створення лоту"+'|'+"Регіон"+'|'+"Планова дата продажу"+'|'+"Тип продажу"+'|'
            +"Стадія роботи"+'|'+"Продаж"+'|'+"Заблоковано"+'|'+"Продано"+'|'+"Коментар"+'|'+"Співробітник"+'|'+"Клієнт");
    for(int i=0;i<lots.size();i++){
        strLots.add(lots.get(i).getId().toString()+'|'+lots.get(i).toString());
    }
    System.out.print(strLots);
    return strLots;
}
    @RequestMapping(value ="/createLot", method = RequestMethod.GET)
    public String createLot(HttpSession session, Model model) {
        if (!isAuth(session)){
            return "logPage";}
        else{
            Employee employee =getCurrEmployee(session);
            model.addAttribute("empl", employee);
            return "Creator";}
    }
    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.HEAD})
    public void creator() {
        /*Employee empl1 =new Employee("Київ", "Омельченко Олександр Олександрович", "mer","a44n73","admin");
        Employee empl2 =new Employee("Львів", "Опанасенко Вячеслав Володимирович","slava","111","manager");
        Employee empl3 =new Employee("Кіровоград", "Шніткова Світлана Василівна", "sv","222","user");
        Client client1 = new Client("0123456789", "МЕ697453","Oprishko","Valerii","Pavlovich" );
        Client client2 = new Client("1123456789", "МЕ697353","Grigorishin","Valerii","Pavlovich" );
        Client client3 = new Client("2123456789", "МЕ697253","Mytko","Valerii","Pavlovich" );
        Client client4 = new Client("3123456789", "МЕ697953","Voronin","Valerii","Pavlovich" );
        employeeService.createEmployee(empl1);
        employeeService.createEmployee(empl2);
        employeeService.createEmployee(empl3);
        clientService.createClient(client1);
        clientService.createClient(client2);
        clientService.createClient(client3);
        clientService.createClient(client4);*/
        Lot lot1 = new Lot("Київ", LocalDateTime.now(), "Безпосередній продаж", "комментарии сотрудиков", employeeService.getEmployee(1L), clientService.getClient(1L));
        Lot lot2 = new Lot("Львів", LocalDateTime.now(), "Відступлення права вимоги", "комментарии сотрудиков", employeeService.getEmployee(2L), clientService.getClient(2L));
        Lot lot3 = new Lot("Миколаїв", LocalDateTime.now(), "Організовані місця продажу", "комментарии сотрудиков", employeeService.getEmployee(3L), clientService.getClient(3L));
        Lot lot4 = new Lot("Одеса", LocalDateTime.now(), "Відкриті торги(аукціон)", "комментарии сотрудиков", employeeService.getEmployee(1L), clientService.getClient(4L));
        lotService.createLot(lot1);
        lotService.createLot(lot2);
        lotService.createLot(lot3);
        lotService.createLot(lot4);

        Equipment equipment1 = new Equipment("нерухомість");
        Equipment equipment2 = new Equipment("автомобиль");
        Equipment equipment3 = new Equipment("мебель");
        Equipment equipment4 = new Equipment("монети");
        Equipment equipment5 = new Equipment("нерухомість");
        Equipment equipment6 = new Equipment("кредит");

        equipment1.setLot(lotService.getLot(1l));
        equipment2.setLot(lotService.getLot(2l));
        equipment3.setLot(lotService.getLot(3l));
        equipment4.setLot(lotService.getLot(4l));
        equipment5.setLot(lotService.getLot(1l));

        equipmentService.createEquipment(equipment1);
        equipmentService.createEquipment(equipment2);
        equipmentService.createEquipment(equipment3);
        equipmentService.createEquipment(equipment4);
        equipmentService.createEquipment(equipment5);
        equipmentService.createEquipment(equipment6);

    }
}