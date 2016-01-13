package nadrabank.dao;

import nadrabank.domain.Employee;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    private SessionFactory factory;

    public EmployeeDaoImpl(){
    }
    public EmployeeDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Employee employee) {
        return (Long)factory.getCurrentSession().save(employee);
    }
    @Override
    public Employee read(Long id) {
        return (Employee)factory.getCurrentSession().get(Employee.class, id);
    }
    @Override
    public boolean update(Employee employee) {
        factory.getCurrentSession().update(employee);
        return true;
    }
    @Override
    public boolean delete(Employee employee) {
        factory.getCurrentSession().delete(employee);
        return true;
    }
    @Override
    public List<Employee> findAll() {
        List<Employee>list;
        list =factory.getCurrentSession().createQuery("from nadrabank.domain.Employee").list();
        return list;
    }
    @Override
    public boolean isExist(String login, String password) {
        Query query = factory.getCurrentSession().createQuery
       ("from nadrabank.domain.Employee emp where emp.login=:log and emp.password=:pass");
        query.setParameter("log",login);
        query.setParameter("pass",password);
        return !query.list().isEmpty();
    }
}