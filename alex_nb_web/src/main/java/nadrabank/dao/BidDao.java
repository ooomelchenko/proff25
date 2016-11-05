package nadrabank.dao;

import nadrabank.domain.Asset;
import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;

import java.util.List;

public interface BidDao {
    Long create(Bid bid);
    Bid read(Long id);
    boolean update(Bid bid);
    boolean delete(Bid bid);
    List findAll();

    Long countOfLots(Bid bid);

    List<Lot> lotsByBid(Bid bid);

    List<Asset> assetsByBid(Bid bid);

    List<Exchange> getBidsByExchange(Exchange exchange);
}