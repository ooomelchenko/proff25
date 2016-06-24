package nadrabank.service;

import nadrabank.dao.CreditDao;
import nadrabank.dao.CreditDaoImpl;
import nadrabank.domain.Credit;
import nadrabank.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //(name ="creditServiceImpl")
@Transactional
public class CreditServiceImpl implements CreditService {
    @Autowired
    private CreditDao creditDao;

    public CreditServiceImpl() {
    }
    public CreditServiceImpl(CreditDaoImpl creditDao) {
        this.creditDao = creditDao;
    }

    public CreditDao getCreditDao() {
        return creditDao;
    }
    public void setCreditDao(CreditDaoImpl creditDao) {
        this.creditDao = creditDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Credit getCredit(Long id) {
        return creditDao.read(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Credit getByInventar(String invNum) {
        return creditDao.findByInventar(invNum);
    }
    @Override
    public boolean createCredit(Credit credit) {
        creditDao.create(credit);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        creditDao.delete(creditDao.read(id));
        return true;
    }
    @Override
    public boolean delete(Credit credit) {
        creditDao.delete(credit);
        return true;
    }
    @Override
    public boolean updateCredit(Credit credit) {
      return  creditDao.update(credit);
    }
    @Override
    @Transactional(readOnly = true)
    public List <Credit> getAllCredits(){
        return creditDao.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List <Credit> getCreditsByPortion(int num){
        return creditDao.findAll(num);
    }
    @Override
    @Transactional(readOnly = true)
    public Long getTotalCountOfCredits(){
       return creditDao.totalCount();
    }
    @Override
    @Transactional(readOnly = true)
    public List showEquip() {
        return creditDao.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List getRegions() {
        return creditDao.getRegions();
    }
    @Override
    @Transactional(readOnly = true)
    public List getTypes() { return creditDao.getTypes();}
    @Override
    @Transactional(readOnly = true)
    public List getCurrencys() {
        return  creditDao.getCur();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Credit> selectCredits(String [] types, String [] regs, String [] currs, int dpdmin, int dpdmax, double zbmin, double zbmax) {
        return creditDao.selectCredits(types, regs, currs, dpdmin, dpdmax, zbmin, zbmax);
    }
    @Override
    @Transactional(readOnly = true)
    public List getCreditsResults(String [] types, String [] regs, String [] currs, int dpdmin, int dpdmax, double zbmin, double zbmax){
        return creditDao.selectCrdSum(types, regs, currs, dpdmin, dpdmax, zbmin, zbmax);
    }
    @Override
    public boolean addCreditsToLot(Lot lot, String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax){
        return creditDao.addCreditsToLot(lot, types, regions, cur, dpdMin, dpdMax, zbMin, zbMax);
    }
    @Override
    @Transactional(readOnly = true)
    public List getCreditsByClient(String inn, Long id){
        return creditDao.getCreditsByClient(inn, id);
    }
}