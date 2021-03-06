package hw7.springnotes.dao;

import hw7.springnotes.domain.Notebook;

import java.util.List;

/**
 * Created by ivan on 24.06.15.
 * Long create(Notebook notebook)
 * Notebook read(Long id)
 * boolean update(Notebook notebook)
 * boolean delete(Notebook notebook)
 * List findAll()
 */
public interface NotebookDao {

    Long create(Notebook notebook);

    Notebook read(Long id);

    boolean update(Notebook notebook);

    boolean delete(Notebook notebook);

    List findAll();

}
