package session13.Task1.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import session13.Task1.domain.Car;

import java.util.List;

/**
 * Created by ivan on 30.06.15.
 */
public class CarDaoImpl implements CarDao {


    private static Logger log = Logger.getLogger(CompanyDaoImpl.class);

    private SessionFactory factory;

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    private void checkFactory() {
        if (factory == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("session13/context-db.xml");
            factory = (SessionFactory) context.getBean("sf");
        }
    }

    @Override
    public Long create(Car car) {
        checkFactory();
        Session session = factory.openSession();
        Long id = null;
        try {
            session.beginTransaction();
            id = (Long) session.save(car);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException e) {
            log.error("ERROR: Cannot create new car - TRANSACTION FAILED.", e);
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.info(session);
        return id;
    }

    @Override
    public Car read(Long id) {
        checkFactory();
        Session session = factory.openSession();
        try {
            return (Car) session.get(Car.class, id);
        } catch (HibernateException e) {
            log.error("ERROR: Cannot read car by id: " + id + ". READ FAILED.", e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.info(session);
        return null;
    }

    @Override
    public boolean update(Car car) {
        checkFactory();
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
            log.info(session);
            return true;
        } catch (HibernateException e) {
            log.error("ERROR: Cannot update car: " + car + ". UPDATE FAILED.", e);
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Car car) {
        checkFactory();
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            log.error("ERROR: Cannot delete car: " + car + ". DELETE FAILED.", e);
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Car> findAll() {
        checkFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("from session13.Task1.domain.Car");
        return query.list();
    }

    public static void main(String[] args) {

    }

}
