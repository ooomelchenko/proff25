package nadrabank.dao;

import nadrabank.domain.Equipment;

import java.util.List;

public interface EquipmentDao {
    Long create(Equipment equipment);
    Equipment read(Long id);
    boolean update(Equipment equipment);
    boolean delete(Equipment equipment);
    List findAll();

    Equipment findByInventar(String invNum);
}
