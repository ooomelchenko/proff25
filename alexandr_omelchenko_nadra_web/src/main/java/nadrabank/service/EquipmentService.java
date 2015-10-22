package nadrabank.service;

import nadrabank.domain.Equipment;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 10/15/2015.
 */
public interface EquipmentService {
    Equipment getEquipment(Long id);

    boolean createTestEquipment();

    boolean createEquipment(Equipment equipment);
    boolean delete(Long id);
    void updateEquipment(Equipment equipment);
    List showEquip();
}
