package nadrabank.dao;

import nadrabank.domain.Client;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by HP on 10/17/2015.
 */
@Repository
public class ClientDaoImpl implements  ClientDao {
    @Autowired
    private SessionFactory factory;

    public ClientDaoImpl(){
    }
    public ClientDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Client client) {
        return (Long)factory.getCurrentSession().save(client);
    }
    @Override
    public Client read(Long id) {
        return (Client)factory.getCurrentSession().get(Client.class, id);
    }
    @Override
    public boolean update(Client client) {
        factory.getCurrentSession().update(client);
        return true;
    }
    @Override
    public boolean delete(Client client) {
        factory.getCurrentSession().delete(client);
        return true;
    }
    @Override
    public List findAll() {
        List<Client>list;
        list =factory.getCurrentSession().createQuery("from nadrabank.domain.Client").list();
        return list;
    }
}
