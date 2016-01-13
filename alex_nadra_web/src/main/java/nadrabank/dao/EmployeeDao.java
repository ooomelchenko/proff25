package nadrabank.dao;

import nadrabank.domain.Employee;

import java.util.List;

public interface EmployeeDao{
    Long create(Employee employee);
    Employee read(Long id);
    boolean update(Employee employee);
    boolean delete(Employee employee);
    List<Employee> findAll();
    boolean isExist (String login, String password);
}