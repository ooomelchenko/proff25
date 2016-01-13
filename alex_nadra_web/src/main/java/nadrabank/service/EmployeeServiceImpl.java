package nadrabank.service;

import nadrabank.dao.EmployeeDao;
import nadrabank.dao.EmployeeDaoImpl;
import nadrabank.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //(name ="EmployeeServiceImpl")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public EmployeeServiceImpl() {
    }
    public EmployeeServiceImpl(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
    public void setEmployeeDao(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(Long id) {
        return employeeDao.read(id);
    }
    @Override
    public boolean createTestEmployee() {
        employeeDao.create(new Employee());
        return true;
    }
    @Override
    public boolean createEmployee(Employee employee) {
        employeeDao.create(employee);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        employeeDao.delete(employeeDao.read(id));
        return true;
    }
    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }
    @Override
    public List showEmployee() {
        return employeeDao.findAll();
    }

    @Override
    public boolean isExist(String l, String p) {
        return employeeDao.isExist(l, p);
    }
}