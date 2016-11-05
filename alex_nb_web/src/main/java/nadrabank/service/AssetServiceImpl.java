package nadrabank.service;

import nadrabank.dao.AssetDao;
import nadrabank.dao.AssetDaoImpl;
import nadrabank.dao.AssetHistoryDao;
import nadrabank.domain.Asset;
import nadrabank.domain.AssetHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service //(name ="assetServiceImpl")
@Transactional
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetHistoryDao assetHistoryDao;
    @Autowired
    private AssetDao assetDao;

    public AssetServiceImpl() {
    }
    public AssetServiceImpl(AssetDaoImpl assetDao) {
        this.assetDao = assetDao;
    }

    public AssetDao getAssetDao() {
        return assetDao;
    }
    public void setAssetDao(AssetDao assetDao) {
        this.assetDao = assetDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Asset getAsset(Long id) {
        return assetDao.read(id);
    }
    @Override
    public boolean createAsset(Asset asset) {
        assetDao.create(asset);
        return true;
    }
    @Override
    public boolean createAsset(String userName, Asset asset) {
        assetHistoryDao.create(new AssetHistory(userName, asset));
        assetDao.create(asset);
        return true;
    }
    @Override
    public boolean updateAsset(Asset asset) {
        return  assetDao.update(asset);
    }
    @Override
    public boolean updateAsset(String userName, Asset asset) {
        assetHistoryDao.create(new AssetHistory(userName, asset));
        return  assetDao.update(asset);
    }
    @Override
    public boolean delete(Long id) {
        assetDao.delete(assetDao.read(id));
        return true;
    }
    @Override
    public boolean delete(Asset asset) {
        assetDao.delete(asset);
        return true;
    }
    @Override
    @Transactional(readOnly = true)
    public List <Asset> getAssetsByPortion(int num){
        return assetDao.findAll(num);
    }
    @Override
    @Transactional(readOnly = true)
    public Long getTotalCountOfAssets(){
       return assetDao.totalCount();
    }

    @Override
    public BigDecimal getTotalSumOfAssets() {
        return assetDao.totalSum();
    }

    @Override
    @Transactional(readOnly = true)
    public List getAll() {
        return assetDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List findAllSuccessBids(Date startBidDate, Date endBidDate) {
        return assetDao.findAllSuccessBids(startBidDate, endBidDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List getRegions() {
        return assetDao.getRegions();
    }

    @Override
    @Transactional(readOnly = true)
    public List getTypes() { return assetDao.getTypes();}

    @Override
    @Transactional(readOnly = true)
    public List getAssetsByInNum(String inn){
        return assetDao.getAssetsByINum(inn);
    }

    @Override
    @Transactional(readOnly = true)
    public List getAllBidDates(){
        return assetDao.getAllBidDates();
    }

    @Override
    @Transactional(readOnly = true)
    public List getExchanges(){
        return assetDao.getExchanges();
    }

    @Override
    @Transactional(readOnly = true)
    public List getDecisionNumbers(){
        return assetDao.getDecisionNumbers();
    }
}