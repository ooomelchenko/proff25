package nadrabank.dao;

import nadrabank.domain.Equipment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EquipmentDaoImpl implements EquipmentDao {
    @Autowired
    private SessionFactory factory;

    public EquipmentDaoImpl(){
    }
    public EquipmentDaoImpl(SessionFactory factory){
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Equipment equipment) {
        return (Long)factory.getCurrentSession().save(equipment);
    }
    @Override
    public Equipment read(Long id) {
        return (Equipment)factory.getCurrentSession().get(Equipment.class, id);
    }
    @Override
    public boolean update(Equipment equipment) {
        factory.getCurrentSession().update(equipment);
        return true;
    }
    @Override
    public boolean delete(Equipment equipment) {
        factory.getCurrentSession().delete(equipment);
        return true;
    }
    @Override
    public List findAll() {
        List<Equipment>list;
        list =factory.getCurrentSession().createQuery("from nadrabank.domain.Equipment").list();
        return list;
    }
}