package nadrabank.service;

import nadrabank.dao.PayDao;
import nadrabank.domain.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PayServiceImpl implements PayService {
    @Autowired
    private PayDao payDao;

    public PayServiceImpl() {
    }
    public PayServiceImpl(PayDao payDao) {
        this.payDao=payDao;
    }

    public PayDao getPayDao() {
        return payDao;
    }
    public void setPayDao(PayDao payDao) {
        this.payDao = payDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Pay getPay(Long id) {
        return payDao.read(id);
    }
    @Override
    public Long createPay(Pay pay) {
        return payDao.create(pay);
    }
    @Override
    public boolean delete(Long id) {
        return payDao.delete(payDao.read(id));
    }
    @Override
    public boolean delete(Pay pay) {
        return payDao.delete(pay);
    }
    @Override
    public boolean updatePay(Pay pay) {
        return payDao.update(pay);
    }
    @Transactional(readOnly = true)
    @Override
    public List getAllPays() {
        return payDao.findAll();
    }

}