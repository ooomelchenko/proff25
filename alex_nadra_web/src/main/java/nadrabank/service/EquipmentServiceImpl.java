package nadrabank.service;

import nadrabank.dao.EquipmentDao;
import nadrabank.dao.EquipmentDaoImpl;
import nadrabank.domain.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //(name ="equipmentServiceImpl")
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;

    public EquipmentServiceImpl() {
    }
    public EquipmentServiceImpl(EquipmentDaoImpl equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    public EquipmentDao getEquipmentDao() {
        return equipmentDao;
    }
    public void setEquipmentDao(EquipmentDaoImpl equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Equipment getEquipment(Long id) {
        return equipmentDao.read(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Equipment getByInventar(String invNum) {
        return equipmentDao.findByInventar(invNum);
    }
    @Override
    public boolean createTestEquipment() {
        equipmentDao.create(new Equipment());
        return true;
    }
    @Override
    public boolean createEquipment(Equipment equipment) {
        equipmentDao.create(equipment);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        equipmentDao.delete(equipmentDao.read(id));
        return true;
    }
    @Override
    public void updateEquipment(Equipment equipment) {
        equipmentDao.update(equipment);
    }
    @Override
    public List showEquip() {
        return equipmentDao.findAll();
    }

}