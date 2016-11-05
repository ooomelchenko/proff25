package nadrabank.dao;

import nadrabank.domain.LotHistory;

import java.util.List;

public interface LotHistoryDao {
    Long create(LotHistory lotHistory);
    LotHistory read(Long id);
    boolean update(LotHistory lotHistory);
    boolean delete(LotHistory lotHistory);

    List getAllBidsId(Long lotId);
}