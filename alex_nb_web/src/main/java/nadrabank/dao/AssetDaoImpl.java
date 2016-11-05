package nadrabank.dao;

import nadrabank.domain.Asset;
import nadrabank.domain.Lot;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class AssetDaoImpl implements AssetDao {
    @Autowired
    private SessionFactory factory;

    public AssetDaoImpl(){
    }
    public AssetDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Asset asset) {
        return (Long)factory.getCurrentSession().save(asset);
    }
    @Override
    public Asset read(Long id) {
        return (Asset)factory.getCurrentSession().get(Asset.class, id);
    }
    @Override
    public boolean update(Asset asset) {
        factory.getCurrentSession().update(asset);
        return true;
    }
    @Override
    public boolean delete(Asset asset) {
        factory.getCurrentSession().delete(asset);
        return true;
    }
    @Override
    public int delAssetsFromLot(Lot lot) {
        Query query =factory.getCurrentSession().createQuery("UPDATE nadrabank.domain.Asset asset SET asset.lot=null, asset.factPrice=null WHERE asset.lot=:lot ");
        query.setParameter("lot", lot);
        return query.executeUpdate();
    }
    @Override
    public List findAll() {
        List<Asset>list;
        list = factory.getCurrentSession().createQuery("SELECT asset FROM nadrabank.domain.Asset asset LEFT JOIN asset.lot lot LEFT JOIN lot.bid bid ORDER BY bid.bidDate, asset.fondDecisionDate").list();
        return list;
    }
    @Override
    public List findAllSuccessBids(Date startBids, Date endBids) {
        Query query = factory.getCurrentSession().createQuery("SELECT asset FROM nadrabank.domain.Asset asset LEFT JOIN asset.lot lot LEFT JOIN lot.bid bid WHERE lot.status!='Торги не відбулись' and lot.bid.bidDate>=:startBid AND lot.bid.bidDate<=:endBid ORDER BY bid.bidDate");
        query.setParameter("startBid", startBids);
        query.setParameter("endBid", endBids);
        return query.list();
    }
    @Override
    public List findAll(int portionNum) {
        Query query = factory.getCurrentSession().createQuery("SELECT asset FROM nadrabank.domain.Asset asset LEFT JOIN asset.lot lot LEFT JOIN lot.bid bid ORDER BY bid.bidDate, asset.fondDecisionDate");
        query.setFirstResult(portionNum*5000);
        query.setMaxResults(5000);
        List<Asset>list;
        list =(List<Asset>)query.list();
        return list;
    }
    @Override
    public Long totalCount() {
        return (Long)factory.getCurrentSession().createQuery("SELECT count(asset.id) from nadrabank.domain.Asset asset").list().get(0);
    }
    @Override
    public BigDecimal totalSum() {
        return (BigDecimal)factory.getCurrentSession().createQuery("SELECT sum(asset.rv) from nadrabank.domain.Asset asset").list().get(0);
    }
    @Override
    public List getRegions() {
        return factory.getCurrentSession().createQuery("SELECT cr.region  FROM nadrabank.domain.Asset cr GROUP BY cr.region ORDER BY cr.region").list();
    }
    @Override
    public List getTypes() {
        return factory.getCurrentSession().createQuery("SELECT cr.asset_name  FROM nadrabank.domain.Asset cr GROUP BY cr.asset_name ORDER BY cr.asset_name").list();
    }
    @Override
    public int delCRDTS(Lot lot) {
        Query query =factory.getCurrentSession().createQuery("UPDATE nadrabank.domain.Asset crdt SET crdt.lot=null WHERE crdt.lot=:lot ");
        query.setParameter("lot", lot);
        return query.executeUpdate();
    }
    @Override
    public List getAssetsByINum(String inn){
            Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Asset asset WHERE asset.lot is null and asset.inn=:inn");
            query.setParameter("inn", inn);
            return query.list();
    }
    @Override
    public List getAllBidDates(){
        return factory.getCurrentSession().createQuery("SELECT asset.lot.bid.bidDate FROM nadrabank.domain.Asset asset GROUP BY asset.lot.bid.bidDate ORDER BY asset.lot.bid.bidDate").list();
    }
    @Override
    public List getExchanges(){
        return factory.getCurrentSession().createQuery("SELECT asset.lot.bid.exchange.companyName FROM nadrabank.domain.Asset asset GROUP BY asset.lot.bid.exchange.companyName ORDER BY asset.lot.bid.exchange.companyName").list();
    }
    @Override
    public List getDecisionNumbers(){
        return factory.getCurrentSession().createQuery("SELECT asset.decisionNumber FROM nadrabank.domain.Asset asset GROUP BY asset.decisionNumber ORDER BY asset.decisionNumber").list();
    }
}