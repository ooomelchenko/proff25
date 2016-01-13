package nadrabank.service;

import nadrabank.domain.Employee;

import java.util.List;

/**
 * Created by HP on 10/19/2015.
 */
public interface EmployeeService {
    Employee getEmployee(Long id);
    boolean createTestEmployee();
    boolean createEmployee(Employee employee);
    boolean delete(Long id);
    void updateEmployee(Employee employee);
    List showEmployee();
    boolean isExist(String l, String p);
}
