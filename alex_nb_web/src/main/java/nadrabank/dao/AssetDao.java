package nadrabank.dao;

import nadrabank.domain.Asset;
import nadrabank.domain.Lot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AssetDao {
    Long create(Asset asset);
    Asset read(Long id);
    boolean update(Asset asset);
    boolean delete(Asset asset);

    int delAssetsFromLot(Lot lot);

    List findAll();

    List findAllSuccessBids(Date startBids, Date endBids);

    List findAll(int portionNum);

    Long totalCount();
    BigDecimal totalSum();

    List getRegions();
    List getTypes();

    int delCRDTS(Lot lot);

    List getAssetsByINum(String inn);

    List getAllBidDates();

    List getExchanges();

    List getDecisionNumbers();
}