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
    public boolean createTestLot() {
        lotDao.create(new Lot());
        return true;
    }
    @Override
    public boolean createLot(Lot lot) {
        lotDao.create(lot);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        lotDao.delete(lotDao.read(id));
        return true;
    }
    @Override
    public void updateLot(Lot lot) {
        lotDao.update(lot);
    }
    @Override
    public List showLot() {
        return lotDao.findAll();
    }
}