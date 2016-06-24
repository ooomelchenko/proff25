package nadrabank.service;

import nadrabank.domain.Bid;
import nadrabank.domain.Exchange;
import nadrabank.domain.Lot;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface LotService {
    Lot getLot(Long id);
    Long createLot(Lot lot);
    boolean delete(Long id);
    boolean delete(Lot lot);
    boolean updateLot(Lot lot);
    List getLots();
    BigDecimal lotSum(Lot lot);
    Long lotCount(Lot lot);

    boolean delLot(Lot lot);

    boolean delLot(Long lotId);

    List getAssetsByLot(Lot lot);

    List getAssetsByLot(Long lotId);

    @Transactional(readOnly = true)
    List getLotsByBidDate(Date first, Date last);

    @Transactional(readOnly = true)
    BigDecimal paymentsSumByLot(Lot lot);

    @Transactional(readOnly = true)
    List paymentsByLot(Lot lot);

    @Transactional(readOnly = true)
    List getLotsByBid(Bid bid);

    @Transactional(readOnly = true)
    List getLotsByExchange(Exchange exchange);
}