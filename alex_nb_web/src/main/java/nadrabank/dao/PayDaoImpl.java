package nadrabank.dao;

import nadrabank.domain.Lot;
import nadrabank.domain.Pay;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
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
        Query query = factory.getCurrentSession().createQuery("SELECT sum(pay.paySum) FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId");
        query.setParameter("lotId", lot.getId());
        return (BigDecimal)query.list().get(0);
    }
    @Override
    public List getPaymentsByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId ORDER BY pay.date DESC");
        query.setParameter("lotId", lot.getId());
        return query.list();
    }
    @Override
    public BigDecimal sumByLotFromBid(Long lotId){
        Query query = factory.getCurrentSession().createQuery("SELECT sum(pay.paySum) FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId AND pay.paySource=:source");
        query.setParameter("source", "Біржа");
        query.setParameter("lotId", lotId);
        return (BigDecimal)query.list().get(0);
    }
    @Override
    public BigDecimal sumByLotFromCustomer(Long lotId){
        Query query = factory.getCurrentSession().createQuery("SELECT sum(pay.paySum) FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId AND (pay.paySource=:source OR pay.paySource is null)");
        query.setParameter("source", "Покупець");
        query.setParameter("lotId", lotId);
        return (BigDecimal)query.list().get(0);
    }

    @Override
    public Date getLastDateByBid(Long lotId) {
        List list;
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId AND pay.paySource=:source ORDER BY pay.date DESC");
        query.setParameter("source", "Біржа");
        query.setParameter("lotId", lotId);
        list = query.list();
        if (list.size() > 0) {
            Pay pay = (Pay) list.get(0);
                return pay.getDate();
        }
        else
            return null;
    }
    @Override
    public Date getLastDateByCustomer(Long lotId) {
        List list;
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Pay pay WHERE pay.lotId=:lotId AND (pay.paySource=:source OR pay.paySource is null) ORDER BY pay.date DESC");
        query.setParameter("source", "Покупець");
        query.setParameter("lotId", lotId);
        list = query.list();
        if (list.size() > 0) {
            Pay pay = (Pay) list.get(0);
            return pay.getDate();
        }
        else
            return null;
    }

}