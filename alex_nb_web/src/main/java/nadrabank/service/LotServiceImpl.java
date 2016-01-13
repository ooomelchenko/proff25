package nadrabank.service;

import nadrabank.dao.LotDao;
import nadrabank.dao.LotDaoImpl;
import nadrabank.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //(name ="LotServiceImpl")
@Transactional
public class LotServiceImpl implements LotService {
    @Autowired
    private LotDao lotDao;

    public LotServiceImpl() {
    }
    public LotServiceImpl(LotDaoImpl lotDao) {
        this.lotDao = lotDao;
    }

    public LotDao getLotDao() {
        return lotDao;
    }
    public void setLotDao(LotDaoImpl lotDao) {
        this.lotDao = lotDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Lot getLot(Long id) {
        return lotDao.read(id);
    }
    @Override
    public Long createLot(Lot lot) {
        return  lotDao.create(lot);
    }
    @Override
    public boolean delete(Long id) {
        Lot lot = lotDao.read(id);
        lotDao.delete(lot);
        return true;
    }
    @Override
    public boolean delete(Lot lot) {
        lotDao.delete(lot);
        return true;
    }
    @Override
    public void updateLot(Lot lot) {
        lotDao.update(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public List getLots() {
        return lotDao.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Double lotSum(Lot lot){
        return lotDao.lotSum(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public Long lotCount(Lot lot){
        return lotDao.lotCount(lot);
    }

    @Override
    public boolean delLot(Lot lot) {
        int cr=lotDao.delCRDTS(lot);
        boolean lt=lotDao.delete(lot);
        return lt;
    }
    @Override
    public boolean delLot(Long lotId) {
        Lot lot = getLot(lotId);
        int cr=lotDao.delCRDTS(lot);
        boolean lt=lotDao.delete(lot);
        return lt;
    }
}