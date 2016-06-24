package nadrabank.service;

import nadrabank.dao.BidDao;
import nadrabank.dao.LotDao;
import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BidServiceImpl implements BidService {
    @Autowired
    private BidDao bidDao;
    @Autowired
    private LotDao lotDao;

    public BidServiceImpl() {
    }
    public BidServiceImpl(BidDao bidDao) {
        this.bidDao=bidDao;
    }

    public BidDao getBidDao() {
        return bidDao;
    }
    public void setBidDao(BidDao bidDao) {
        this.bidDao = bidDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Bid getBid(Long id) {
        return bidDao.read(id);
    }
    @Override
    public Long createBid(Bid bid) {
        return bidDao.create(bid);
    }
    @Override
    public boolean delete(Long id) {
        return bidDao.delete(bidDao.read(id));
    }
    @Override
    public boolean delete(Bid bid) {
        return bidDao.delete(bid);
    }
    @Override
    public boolean updateBid(Bid bid) {
        return bidDao.update(bid);
    }
    @Transactional(readOnly = true)
    @Override
    public List getAllBids() {
        return bidDao.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public List lotsByBid(Bid bid){
        return bidDao.lotsByBid(bid);
    }
    @Transactional(readOnly = true)
    @Override
    public Long countOfLots(Bid bid) {
        return bidDao.countOfLots(bid);
    }
    @Transactional(readOnly = true)
    @Override
    public BigDecimal sumByBid(Bid bid){
        BigDecimal sum= new BigDecimal(0);
        List<Lot> lotList= bidDao.lotsByBid(bid);
        for(Lot lot: lotList){
           sum= sum.add(lotDao.lotSum(lot));
        }
        return sum;
    }
    @Transactional(readOnly = true)
    @Override
    public List getBidsByExchange(Exchange exchange){
        return bidDao.getBidsByExchange(exchange);
    }
}