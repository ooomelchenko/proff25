package nadrabank.dao;

import nadrabank.domain.Lot;
import nadrabank.domain.Pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PayDao {
    Long create(Pay pay);
    Pay read(Long id);
    boolean update(Pay pay);
    boolean delete(Pay pay);
    List findAll();

    BigDecimal sumByLot(Lot lot);

    BigDecimal sumByLotFromBid(Long lotId);

    BigDecimal sumByLotFromCustomer(Long lotId);

    List getPaymentsByLot(Lot lot);

    Date getLastDateByBid(Long lotId);

    Date getLastDateByCustomer(Long lotId);
}