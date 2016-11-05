package nadrabank.dao;

import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
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
    public List<Lot> findAll() {
        return factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot lot ORDER BY lot.bid.bidDate DESC , lot.bid.exchange.companyName").list();
    }
    @Override
    public List<Long> findAllId() {
        return factory.getCurrentSession().createQuery("SELECT lot.id FROM nadrabank.domain.Lot lot ORDER BY lot.id DESC").list();
    }
    @Override
    public BigDecimal lotSum(Lot lot){
        Query queryS;
        if(lot.getLotType()==0) {
            queryS = factory.getCurrentSession().createQuery("SELECT sum(cr.rv) FROM nadrabank.domain.Credit cr WHERE cr.lot=:lot");
            queryS.setParameter("lot", lot.getId());
        }
        else if(lot.getLotType()==1) {
            queryS = factory.getCurrentSession().createQuery("SELECT sum(asset.rv) FROM nadrabank.domain.Asset asset WHERE asset.lot=:lot");
            queryS.setParameter("lot", lot);
        }
        else  {
            queryS = factory.getCurrentSession().createQuery("SELECT sum(asset.rv) FROM nadrabank.domain.Asset asset WHERE asset.lot=:lot");
            queryS.setParameter("lot", lot);
        }
        return (BigDecimal)queryS.list().get(0);
    }
    @Override
    public Long lotCount(Lot lot){
        Query query;
        if(lot.getLotType()==0) {
            query = factory.getCurrentSession().createQuery("SELECT count(cr.rv) FROM nadrabank.domain.Credit cr WHERE cr.lot=:lot");
            query.setParameter("lot", lot.getId());
        }
        else if(lot.getLotType()==1) {
            query = factory.getCurrentSession().createQuery("SELECT count(asset.rv) FROM nadrabank.domain.Asset asset WHERE asset.lot=:lot");
            query.setParameter("lot", lot);
        }
        else {
            query = factory.getCurrentSession().createQuery("SELECT count(asset.rv) FROM nadrabank.domain.Asset asset WHERE asset.lot=:lot");
            query.setParameter("lot", lot);
        }
        return (Long) query.list().get(0);
    }
    @Override
    public List getAssetsByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Asset asset Where asset.lot=:lot ORDER BY asset.rv DESC ");
        query.setParameter("lot", lot);
        return query.list();
    }
    @Override
    public List getTMCAssetsByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery
                ("FROM nadrabank.domain.Asset asset Where asset.lot=:lot and asset.assetTypeCode ='10' " +
                        "and (asset.assetGroupCode='104' or asset.assetGroupCode='105' or asset.assetGroupCode='106' or asset.assetGroupCode='107' or asset.assetGroupCode='108' or asset.assetGroupCode='109' or asset.assetGroupCode='1010' or asset.assetGroupCode='1011') " +
                        "ORDER BY asset.rv DESC ");
        query.setParameter("lot", lot);
        return query.list();
    }
    @Override
    public List getNotTMCAssetsByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery
                ("FROM nadrabank.domain.Asset asset Where asset.lot=:lot " +
                        "and (asset.assetGroupCode!='104' and asset.assetGroupCode!='105' and asset.assetGroupCode!='106' and asset.assetGroupCode!='107' and asset.assetGroupCode!='108' and asset.assetGroupCode!='109' and asset.assetGroupCode!='1010' and asset.assetGroupCode!='1011') " +
                        "ORDER BY asset.rv DESC ");
        query.setParameter("lot", lot);
        return query.list();
    }
    @Override
    public List getCRDTSByLot(Lot lot) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Credit credit Where credit.lot=:lot ORDER BY credit.rv DESC ");
        query.setParameter("lot", lot.getId());
        return query.list();
    }
    @Override
    public List<Lot> getLotsByBidDate(Date first, Date last){
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot lot WHERE lot.bid.bidDate>=:date1 AND lot.bid.bidDate<=:date2");
        query.setParameter("date1", first);
        query.setParameter("date2", last);
        return query.list();
    }
    @Override
    public List getLotsByBid(Bid bid) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot lot Where lot.bid=:bid");//ORDER BY lot.bid.bidDate DESC
        query.setParameter("bid", bid);
        return query.list();
    }
    @Override
    public List getLotsByExchange(Exchange exchange) {
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Lot lot Where lot.bid.exchange=:exchange");
        query.setParameter("exchange", exchange);
        return query.list();
    }
}