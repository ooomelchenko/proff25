package nadrabank;

import nadrabank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import java.util.Locale;

@Controller
@SessionAttributes("id")
public class Main {
    @Autowired
    private EquipmentService equipmentService;
 //   @Autowired
 //   private LotService lotService;
 //   @Autowired
  //  private ClientService clientService;
 //   @Autowired
 //   private EmployeeService employeeService;

    public EquipmentService getService() {
        return equipmentService;
    }
    public void setService(EquipmentService service) {
        this.equipmentService = service;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        EquipmentService service = context.getBean("equipmentServiceImpl", EquipmentService.class);
        service.createTestEquipment();
    //    LotService lotservice = context.getBean("lotServiceImpl", LotService.class);
  //      lotservice.createTestLot();
   //     ClientService clientservice = context.getBean("clientServiceImpl", ClientService.class);
   //     clientservice.createTestClient();
      //  EmployeeService employeeservice = context.getBean("employeeServiceImpl", EmployeeService.class);
    //    employeeservice.createTestEmployee();
   //     Main head = new Main();
  //      head.equipmentService.createTestEquipment();
  //      head.clientService.createTestClient();
  //      head.employeeService.createTestEmployee();
    }
}