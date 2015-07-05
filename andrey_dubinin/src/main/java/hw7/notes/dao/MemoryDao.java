package hw7.notes.dao;

import hw7.notes.domain.Memory;

import java.util.List;

/**
 * Created by jax on 05.07.15.
 */
public interface MemoryDao {
    Long create(Memory memory);
    Memory read(Long id);
    Boolean update(Memory memory);
    Boolean delete(Memory memory);
    List findAll();
}