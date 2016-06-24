package nadrabank.service;

import nadrabank.domain.Asset;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by HP on 10/15/2015.
 */
public interface AssetService {
    Asset getAsset(Long id);
    boolean createAsset(Asset asset);
    boolean delete(Long id);
    boolean delete(Asset asset);
    boolean updateAsset(Asset asset);

    List <Asset> getAssetsByPortion(int num);

    Long getTotalCountOfAssets();
    BigDecimal getTotalSumOfAssets();

    List getAll();
    List getRegions();
    List getTypes();

    List getAssetsByInNum(String inn);

    @Transactional(readOnly = true)
    List getAllBidDates();

    @Transactional(readOnly = true)
    List getExchanges();
}
