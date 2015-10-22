package nadrabank.dao;

import nadrabank.domain.Employee;

import java.util.List;

/**
 * Created by HP on 10/17/2015.
 */
public interface EmployeeDao{
        Long create(Employee employee);
    Employee read(Long id);
        boolean update(Employee employee);
        boolean delete(Employee employee);
        List findAll();
}