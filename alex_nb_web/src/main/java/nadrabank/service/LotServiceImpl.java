package nadrabank.service;

import nadrabank.dao.AssetDao;
import nadrabank.dao.LotDao;
import nadrabank.dao.LotDaoImpl;
import nadrabank.dao.PayDao;
import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service //(name ="LotServiceImpl")
@Transactional
public class LotServiceImpl implements LotService {
    @Autowired
    private LotDao lotDao;
    @Autowired
    private AssetDao asstDao;
    @Autowired
    private PayDao payDao;

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
    public boolean updateLot(Lot lot) {
       return lotDao.update(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public List getLots() {
        return lotDao.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public BigDecimal lotSum(Lot lot){
        return lotDao.lotSum(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public Long lotCount(Lot lot){
        return lotDao.lotCount(lot);
    }

    @Override
    public boolean delLot(Lot lot) {
        int cr=asstDao.delAssetsFromLot(lot);
        boolean lt=lotDao.delete(lot);
        return lt;
    }
    @Override
    public boolean delLot(Long lotId) {
        Lot lot = getLot(lotId);
        int cr=asstDao.delAssetsFromLot(lot);
        boolean lt=lotDao.delete(lot);
        return lt;
    }
    @Override
    @Transactional(readOnly = true)
    public List getAssetsByLot(Lot lot){
        return lotDao.getAssetsByLot(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public List getAssetsByLot(Long lotId){
        return lotDao.getAssetsByLot(lotDao.read(lotId));
    }
    @Override
    @Transactional(readOnly = true)
    public List getLotsByBidDate(Date first, Date last){
        return lotDao.getLotsByBidDate(first, last);
    }
    @Override
    @Transactional(readOnly = true)
    public BigDecimal paymentsSumByLot(Lot lot){
        return payDao.sumByLot(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public List paymentsByLot(Lot lot){
        return payDao.getPaymentsByLot(lot);
    }
    @Override
    @Transactional(readOnly = true)
    public List getLotsByBid(Bid bid){
        return lotDao.getLotsByBid(bid);
    }
    @Override
    @Transactional(readOnly = true)
    public List getLotsByExchange(Exchange exchange){
        return lotDao.getLotsByExchange(exchange);
    }

}