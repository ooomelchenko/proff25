package nadrabank;

import nadrabank.domain.Client;
import nadrabank.domain.Employee;
import nadrabank.domain.Equipment;
import nadrabank.domain.Lot;
import nadrabank.service.ClientService;
import nadrabank.service.EmployeeService;
import nadrabank.service.EquipmentService;
import nadrabank.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Main {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private LotService lotService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;

    public static void main(String[] args) {

        /*
        Employee empl1 =new Employee("Київ", "Омельченко Олександр Олександрович", "mer","a44n73","admin");
        Employee empl2 =new Employee("Львів", "Опанасенко Вячеслав Володимирович","slava","111","manager");
        Employee empl3 =new Employee("Кіровоград", "Шніткова Світлана Василівна", "sv","222","user");
        Client client1 = new Client("0123456789", "МЕ697453","Oprishko","Valerii","Pavlovich" );
        Client client2 = new Client("1123456789", "МЕ697353","Grigorishin","Valerii","Pavlovich" );
        Client client3 = new Client("2123456789", "МЕ697253","Mytko","Valerii","Pavlovich" );
        Client client4 = new Client("3123456789", "МЕ697953","Voronin","Valerii","Pavlovich" );
        Lot lot1 = new Lot("Київ", new Date(), "Безпосередній продаж", "комментарии сотрудиков");
        Lot lot2 = new Lot("Львів", new Date(), "Відступлення права вимоги", "комментарии сотрудиков");
        Lot lot3 = new Lot("Миколаїв", new Date(), "Організовані місця продажу", "комментарии сотрудиков");
        Lot lot4 = new Lot("Одеса", new Date(), "Відкриті торги(аукціон)", "комментарии сотрудиков");
        Equipment equipment1 = new Equipment("нерухомість", "4000000", 213432, 116, 12010, "Квартира", "1244-1244", "квартира в центре", "Київ");
        Equipment equipment2 = new Equipment("автомобиль", "3000000", 213429, 110, 12010, "Легковой автомобиль", "1244-1245", "Skoda Oktavia", "Львів");
        Equipment equipment3 = new Equipment("мебель", "2000000", 213411, 110, 12010, "Шкаф", "1244-1246", "квартира в центре", "Одеса");
        Equipment equipment4 = new Equipment("нерухомість", "4000000", 213424, 107, 12010, "Квартира", "1244-1247", "квартира в центре", "Харків");
        Equipment equipment5 = new Equipment("нерухомість", "4000000", 213488, 108, 12010, "Квартира", "1244-1248", "квартира в центре", "Суми");
        Equipment equipment6 = new Equipment("нерухомість", "4000000", 213477, 105, 12010, "Квартира", "1244-1249", "квартира в центре", "Рівне");
        equipment1.setLot(lot1);
        equipment2.setLot(lot2);
        equipment3.setLot(lot3);
        equipment4.setLot(lot4);
        equipment5.setLot(lot1);
        lot1.setEmployee(empl1);
        lot1.setClient(client1);
        lot2.setEmployee(empl2);
        lot2.setClient(client2);
        lot3.setEmployee(empl3);
        lot3.setClient(client3);
        lot4.setEmployee(empl1);
        lot4.setClient(client4);
        employeeService.updateEmployee(empl1);
        employeeService.updateEmployee(empl2);
        employeeService.updateEmployee(empl3);
        clientService.updateClient(client1);
        clientService.updateClient(client2);
        clientService.updateClient(client3);
        clientService.updateClient(client4);
        equipmentService.updateEquipment(equipment1);
        equipmentService.updateEquipment(equipment2);
        equipmentService.updateEquipment(equipment3);
        equipmentService.updateEquipment(equipment4);
        equipmentService.updateEquipment(equipment5);
        equipmentService.updateEquipment(equipment6);
        lotService.updateLot(lot1);
        lotService.updateLot(lot2);
        lotService.updateLot(lot3);
        lotService.updateLot(lot4);*/

    }
}
