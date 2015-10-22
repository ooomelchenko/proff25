package nadrabank.service;

import nadrabank.domain.Client;
import java.util.List;

/**
 * Created by HP on 10/19/2015.
 */
public interface ClientService {
    Client getClient(Long id);
    boolean createTestClient();
    boolean createClient(Client client);
    boolean delete(Long id);
    void updateClient(Client client);
    List showClient();
}
