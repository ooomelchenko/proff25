package nadrabank.controller;

import nadrabank.domain.Equipment;
import nadrabank.service.ClientService;
import nadrabank.service.EmployeeService;
import nadrabank.service.EquipmentService;
import nadrabank.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("idd")
public class NadraController {
//    @Autowired
 //   private EquipmentService equiomentService;
  //  @Autowired
   // private LotService lotService;
 //   @Autowired
 //   private ClientService clientService;
  //  @Autowired
  //  private EmployeeService employeeService;

    @RequestMapping(value = "/json11", method = RequestMethod.POST)
    public @ResponseBody
    List<Equipment> jsonM(@RequestParam("login") String login, @RequestParam("password") String password) {
      //  if (login.equals("mer") && password.equals("111")) {
       //     List<Equipment> equipments = new ArrayList<>();

       //     equipments.add(new Equipment());
     //       equipments.add(new Equipment());
      //      return equipments;
     //   } else {
            return null;
    //    }
    }
}
