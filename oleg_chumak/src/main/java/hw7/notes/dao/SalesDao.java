package hw7.notes.dao;

import hw7.notes.domain.Sales;

import java.util.List;
import java.util.Map;

/**
 * Created by oleg on 24.06.15.
 */
public interface SalesDao {
    Long create(Sales store);
    Sales read(Long ig);
    boolean update(Sales store);
    boolean delete(Sales store);
    List findAll();
    Map getSalesByDays();
}
