package nadrabank.service;

import nadrabank.domain.Asset;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 10/15/2015.
 */
public interface AssetService {
    Asset getAsset(Long id);
    boolean createAsset(Asset asset);
    boolean delete(Long id);
    boolean delete(Asset asset);

    boolean createAsset(String userName, Asset asset);

    boolean updateAsset(Asset asset);

    boolean updateAsset(String userName, Asset asset);

    @Transactional(readOnly = true)
    List <Asset> getAssetsByPortion(int num);

    @Transactional(readOnly = true)
    Long getTotalCountOfAssets();

    @Transactional(readOnly = true)
    BigDecimal getTotalSumOfAssets();

    @Transactional(readOnly = true)
    List getAll();

    @Transactional(readOnly = true)
    List findAllSuccessBids(Date startBidDate, Date endBidDate);

    @Transactional(readOnly = true)
    List getRegions();

    @Transactional(readOnly = true)
    List getTypes();

    @Transactional(readOnly = true)
    List getAssetsByInNum(String inn);

    @Transactional(readOnly = true)
    List getAllBidDates();

    @Transactional(readOnly = true)
    List getExchanges();

    @Transactional(readOnly = true)
    List getDecisionNumbers();
}
