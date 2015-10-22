package nadrabank.dao;

import nadrabank.domain.Lot;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LotDaoImpl implements LotDao {
    @Autowired
    private SessionFactory factory;

    public LotDaoImpl(){
    }
    public LotDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Lot lot) {
        return (Long)factory.getCurrentSession().save(lot);
    }
    @Override
    public Lot read(Long id) {
        return (Lot)factory.getCurrentSession().get(Lot.class, id);
    }
    @Override
    public boolean update(Lot lot) {
        factory.getCurrentSession().update(lot);
        return true;
    }
    @Override
    public boolean delete(Lot lot) {
        factory.getCurrentSession().delete(lot);
        return true;
    }
    @Override
    public List findAll() {
        List<Lot>list;
        list =factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot").list();
        return list;
    }
}