package hw7.notes.dao;

import hw7.notes.domain.Vendor;
import java.util.List;

/**
 * Created by GFalcon on 25.06.15.
 */
public interface VendorDao {
    Long create(Vendor vendor);
    Vendor read(Long ig);
    boolean update(Vendor vendor);
    boolean delete(Vendor vendor);
    List<Vendor> findAll();
}
