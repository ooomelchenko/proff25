package nadrabank.dao;

import nadrabank.domain.Lot;

import java.util.List;

public interface LotDao {
    Long create(Lot lot);
    Lot read(Long id);
    boolean update(Lot lot);
    boolean delete(Lot lot);
    List findAll();
}