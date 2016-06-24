package nadrabank.dao;

import nadrabank.domain.Credit;
import nadrabank.domain.Lot;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Repository
public class CreditDaoImpl implements CreditDao {
    @Autowired
    private SessionFactory factory;

    public CreditDaoImpl(){
    }
    public CreditDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Credit credit) {
        return (Long)factory.getCurrentSession().save(credit);
    }
    @Override
    public Credit read(Long id) {
        return (Credit)factory.getCurrentSession().get(Credit.class, id);
    }
    @Override
    public boolean update(Credit credit) {
        factory.getCurrentSession().update(credit);
        return true;
    }
    @Override
    public boolean delete(Credit credit) {
        factory.getCurrentSession().delete(credit);
        return true;
    }
    @Override
    public List findAll() {
        List<Credit>list;
        list =factory.getCurrentSession().createQuery("from nadrabank.domain.Credit").list();
        return list;
    }
    @Override
    public List findAll(int portionNum) {
        Query query = factory.getCurrentSession().createQuery("from nadrabank.domain.Credit");
        query.setFirstResult(portionNum*5000);
        query.setMaxResults(5000);
        List<Credit>list;
        list =(List<Credit>)query.list();
        return list;
    }
    @Override
    public Long totalCount() {
        return (Long)factory.getCurrentSession().createQuery("SELECT count(cr.id) from nadrabank.domain.Credit cr").list().get(0);
    }
    @Override
    public Credit findByInventar(String invNum){
        return (Credit)factory.getCurrentSession().createQuery("from nadrabank.domain.Credit").list().get(0);
    }
    @Override
    public List getRegions() {
        return factory.getCurrentSession().createQuery("SELECT cr.region  FROM nadrabank.domain.Credit cr GROUP BY cr.region ORDER BY cr.region").list();
    }
    @Override
    public List getTypes() {
        return factory.getCurrentSession().createQuery("SELECT cr.clientType  FROM nadrabank.domain.Credit cr GROUP BY cr.clientType ORDER BY cr.clientType").list();
    }
    @Override
    public List getCur() {
        return factory.getCurrentSession().createQuery("SELECT cr.curr  FROM nadrabank.domain.Credit cr GROUP BY cr.curr").list();
    }
    @Override
    public String makeQueryText(String[] types, String[] regions, String[] cur){
        String queryText="WHERE cr.dpd>=:dpdmin AND cr.dpd<=:dpdmax AND cr.creditPrice>=:zbmin AND cr.creditPrice<=:zbmax AND cr.lot is null ";
        if(types.length!=1 || !types[0].equals("")){
            for (int j= 0; j < types.length; j++) {
                if (types.length==1)
                    queryText = queryText+" AND cr.clientType='" + types[j] + "'";
                else if (j==0&&!types[j].equals(""))
                    queryText = queryText + " AND (cr.clientType='" + types[j] + "'";
                else if(j==types.length-1)
                    queryText = queryText+" OR cr.clientType='" + types[j]+"')";
                else
                    queryText = queryText + " OR cr.clientType='" + types[j] + "'";
            }
        }
        if(regions.length!=1 || !regions[0].equals("")){
            for (int i = 0; i < regions.length; i++) {
                if (regions.length==1)
                    queryText = queryText+" AND cr.region='" + regions[i] + "'";
                else if (i==0)
                    queryText = queryText + " AND (cr.region='" + regions[i] + "'";
                else if(i==regions.length-1)
                    queryText = queryText+" OR cr.region='" + regions[i]+"')";
                else
                    queryText = queryText + " OR cr.region='" + regions[i] + "'";
            }
        }
        if(cur.length!=1 || !cur[0].equals("")) {
            for (int j = 0; j < cur.length; j++) {
                if (cur.length == 1)
                    queryText = queryText + " AND cr.curr='" + cur[j] + "'";
                else if (j == 0)
                    queryText = queryText + " AND (cr.curr='" + cur[j] + "'";
                else if (j == cur.length - 1)
                    queryText = queryText + " OR cr.curr='" + cur[j] + "')";
                else
                    queryText = queryText + " OR cr.curr='" + cur[j] + "'";
            }
        }
        return queryText;
    }
    @Override
    public List selectCredits(String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax) {
        String queryText = makeQueryText(types, regions, cur);

        Query query =factory.getCurrentSession().createQuery("FROM Credit cr "+queryText);
        query.setParameter("dpdmin", dpdMin);
        query.setParameter("dpdmax", dpdMax);
        query.setParameter("zbmin", zbMin);
        query.setParameter("zbmax", zbMax);

        return query.list();
    }
    @Override
    public List selectCrdSum(String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax) {
        String part2 = makeQueryText(types,regions, cur);
        String queryText1="SELECT sum(cr.creditPrice) as zb FROM Credit cr "+part2;
        String queryText2="SELECT count(cr.creditPrice) as kol FROM Credit cr "+part2;
        Query query1 =factory.getCurrentSession().createQuery(queryText1);
        query1.setParameter("dpdmin", dpdMin);
        query1.setParameter("dpdmax", dpdMax);
        query1.setParameter("zbmin", zbMin);
        query1.setParameter("zbmax", zbMax);
        Query query2 =factory.getCurrentSession().createQuery(queryText2);
        query2.setParameter("dpdmin", dpdMin);
        query2.setParameter("dpdmax", dpdMax);
        query2.setParameter("zbmin", zbMin);
        query2.setParameter("zbmax", zbMax);
        ArrayList<String> rezList= new ArrayList<>();
            try{
            double zb=(Double)query1.list().get(0);
            Formatter f = new Formatter();
            rezList.add(f.format("%,.2f", zb).toString());}
            catch (NullPointerException e){
                rezList.add("0");
            }
        Long kol= (Long)query2.list().get(0);
        rezList.add(kol.toString());
        return rezList;
    }
    @Override
    public boolean addCreditsToLot(Lot lot, String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax) {

        String part2 = makeQueryText(types, regions, cur);
        Query query =factory.getCurrentSession().createQuery("UPDATE nadrabank.domain.Credit cr SET cr.lot=:lott "+part2);

        query.setParameter("dpdmin", dpdMin);
        query.setParameter("dpdmax", dpdMax);
        query.setParameter("zbmin", zbMin);
        query.setParameter("zbmax", zbMax);
        query.setParameter("lott", lot);
        int rows =query.executeUpdate();
        return true;
    }
    @Override
    public int delCRDTS(Lot lot) {
        Query query =factory.getCurrentSession().createQuery("UPDATE nadrabank.domain.Credit crdt SET crdt.lot=null WHERE crdt.lot=:lot ");
        query.setParameter("lot", lot);
        return query.executeUpdate();
    }
    @Override
    public List getCreditsByClient(String inn, Long idBars){
        if (!inn.equals("")&&idBars!=null){
        Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Credit crdt WHERE crdt.lot is null and crdt.inn=:inn and crdt.id=:idBars");
            query.setParameter("inn", inn);
            query.setParameter("idBars", idBars);
            return query.list();
        }
        else if(!inn.equals("")&&idBars==null){
            Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Credit crdt WHERE crdt.lot is null and crdt.inn=:inn");
            query.setParameter("inn", inn);
            return query.list();
        }
        else if (inn.equals("")&&idBars!=null){
            Query query = factory.getCurrentSession().createQuery("FROM nadrabank.domain.Credit crdt WHERE crdt.lot is null and crdt.id=:idBars");
            query.setParameter("idBars", idBars);
            return query.list();
        }
       else return null;
    }
}