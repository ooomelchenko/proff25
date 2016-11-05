package nadrabank.dao;

import nadrabank.domain.LotHistory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LotHistoryDaoImpl implements LotHistoryDao {
    @Autowired
    private SessionFactory factory;

    public LotHistoryDaoImpl(){
    }
    public LotHistoryDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(LotHistory lotHistory) {
        return (Long)factory.getCurrentSession().save(lotHistory);
    }
    @Override
    public LotHistory read(Long id) {
        return (LotHistory)factory.getCurrentSession().get(LotHistory.class, id);
    }
    @Override
    public boolean update(LotHistory lotHistory) {
        factory.getCurrentSession().update(lotHistory);
        return true;
    }
    @Override
    public boolean delete(LotHistory lotHistory) {
        factory.getCurrentSession().delete(lotHistory);
        return true;
    }
    @Override
    public List getAllBidsId(Long lotId){
        Query query = factory.getCurrentSession().createQuery("SELECT lotHistory.bidId FROM nadrabank.domain.LotHistory lotHistory Where lotHistory.id=:lotId and lotHistory.bidId is not null GROUP BY lotHistory.bidId ORDER BY max(lotHistory.idKey) DESC");
        query.setParameter("lotId", lotId);
        return query.list();
    }

}