package nadrabank.dao;
import nadrabank.domain.Client;

import java.util.List;

/**
 * Created by HP on 10/17/2015.
 */
public interface ClientDao {
    Long create(Client client);
    Client read(Long id);
    boolean update(Client client);
    boolean delete(Client client);
    List findAll();
}