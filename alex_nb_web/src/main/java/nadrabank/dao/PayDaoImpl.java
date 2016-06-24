package nadrabank.dao;

import nadrabank.domain.Lot;
import nadrabank.domain.Pay;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class PayDaoImpl implements PayDao {
    @Autowired
    private SessionFactory factory;

    public PayDaoImpl() {
    }
    public PayDaoImpl(SessionFactory factory) {
        this.factory=factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Pay pay) {
        return (Long)factory.getCurrentSession().save(pay);
    }
    @Override
    public Pay read(Long id) {
        return (Pay)factory.getCurrentSession().get(Pay.class, id);
    }
    @Override
    public boolean update(Pay pay) {
        factory.getCurrentSession().update(pay);
        return true;
    }
    @Override
    public boolean delete(Pay pay) {
        factory.getCurrentSession().delete(pay);
        return true;
    }
    @Override
    public List findAll() {
        List<Pay>list;
        list =factory.getCurrentSession().createQuery("FROM nadrabank.domain.Pay pay order by pay.date DESC").list();
        return list;
    }
    @Override
    public BigDecimal sumByLot(Lot lot){
        Query query = factory.getCurrentSession().createQuery("SELECT sum(pay.paySum) FROM nadrabank.domain.Pay pay WHERE pay.lot=:lot");
        query.setParameter("lot", lot);
        return (BigDecimal)query.list().get(0);
    }
    @Override
    public List getPaymentsByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Pay pay WHERE pay.lot=:lot ORDER BY pay.date DESC");
        query.setParameter("lot", lot);
        return query.list();
    }
}