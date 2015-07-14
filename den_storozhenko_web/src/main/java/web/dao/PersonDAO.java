package web.dao;

import web.domain.Person;

import java.util.List;

public interface PersonDAO {
    Long create(Person person);
    Person read(Long id);
    boolean update(Person person);
    boolean delete(Person person);
    List getPersonsPorced(int start, int size);
    List findAll();
}
