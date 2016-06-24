package nadrabank.dao;

import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidDaoImpl implements BidDao {
    @Autowired
    private SessionFactory factory;

    public BidDaoImpl() {
    }
    public BidDaoImpl(SessionFactory factory) {
        this.factory=factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Bid bid) {
        return (Long)factory.getCurrentSession().save(bid);
    }
    @Override
    public Bid read(Long id) {
        return (Bid)factory.getCurrentSession().get(Bid.class, id);
    }
    @Override
    public boolean update(Bid bid) {
        factory.getCurrentSession().update(bid);
        return true;
    }
    @Override
    public boolean delete(Bid bid) {
        factory.getCurrentSession().delete(bid);
        return true;
    }
    @Override
    public List findAll() {
        List<Bid>list;
        list =factory.getCurrentSession().createQuery("FROM nadrabank.domain.Bid b order by b.bidDate DESC").list();
        return list;
    }
    @Override
    public Long countOfLots(Bid bid) {
        Query query =factory.getCurrentSession().createQuery("SELECT count(l.id) FROM nadrabank.domain.Lot l WHERE l.bid=:b");
        query.setParameter("b", bid);
        return (Long) query.list().get(0);
    }
    @Override
    public List<Lot> lotsByBid (Bid bid) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot l WHERE l.bid=:b");
        query.setParameter("b", bid);
        return query.list();
    }
    @Override
    public List<Exchange> getBidsByExchange (Exchange exchange){
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Bid bid WHERE bid.exchange=:exchange");
        query.setParameter("exchange", exchange);
        return query.list();
    }
}