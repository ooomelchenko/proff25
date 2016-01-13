package nadrabank.dao;

import nadrabank.domain.Credit;
import nadrabank.domain.Lot;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

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
    public List<Lot> findAll() {
        List list;
        list =factory.getCurrentSession().createQuery("From nadrabank.domain.Lot l ORDER BY l.lotDate DESC ").list();
        return list;
    }
    @Override
    public int delCRDTS(Lot lot) {
        Query query =factory.getCurrentSession().createQuery("UPDATE nadrabank.domain.Credit crdt SET crdt.lot=null WHERE crdt.lot=:lot ");
        query.setParameter("lot", lot);
        int deletedRows = query.executeUpdate();
        return deletedRows;
    }
    @Override
    public Double lotSum(Lot lot){
        Query queryS = factory.getCurrentSession().createQuery("SELECT sum(cr.totalBorg) FROM nadrabank.domain.Credit cr WHERE cr.lot=:lot");
        queryS.setParameter("lot", lot);
        return (Double)queryS.list().get(0);
    }
    @Override
    public Long lotCount(Lot lot){
        Query query = factory.getCurrentSession().createQuery("SELECT count(cr.totalBorg) FROM nadrabank.domain.Credit cr WHERE cr.lot=:lot");
        query.setParameter("lot", lot);
        return (Long) query.list().get(0);
    }
}