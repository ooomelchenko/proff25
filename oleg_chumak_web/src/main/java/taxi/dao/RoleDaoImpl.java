package taxi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import taxi.domain.Role;

import java.util.List;

/**
 * Created by GFalcon on 16.07.15.
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private SessionFactory factory;

    public RoleDaoImpl() {
    }

    public RoleDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public String create(Role role) {
        return (String) factory.getCurrentSession().save(role);
    }


    @Override
    public Role read(String roleName) {
        return (Role) factory.getCurrentSession().get(Role.class, roleName);
    }

    @Override
    public boolean update(Role role) {
        factory.getCurrentSession().update(role);
        return true;
    }

    @Override
    public boolean delete(Role role) {
        factory.getCurrentSession().delete(role);
        return true;
    }

    @Override
    public List<Role> findAll() {
        return factory.getCurrentSession().createQuery("from Role as rl").list();
    }
}
