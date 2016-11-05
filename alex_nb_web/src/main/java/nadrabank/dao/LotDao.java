package nadrabank.dao;

import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface LotDao {
    Long create(Lot lot);
    Lot read(Long id);
    boolean update(Lot lot);
    boolean delete(Lot lot);
    List findAll();

    List<Long> findAllId();

    BigDecimal lotSum(Lot lot);
    Long lotCount(Lot lot);

    List getAssetsByLot(Lot lot);

    List getTMCAssetsByLot(Lot lot);

    List getNotTMCAssetsByLot(Lot lot);

    List getCRDTSByLot(Lot lot);

    List<Lot> getLotsByBidDate(Date first, Date last);

    List getLotsByBid(Bid bid);

    List getLotsByExchange(Exchange exchange);
}