package nadrabank.service;

import nadrabank.dao.ClientDao;
import nadrabank.dao.ClientDaoImpl;
import nadrabank.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by HP on 10/19/2015.
 */
@Service //(name ="ClientServiceImpl")
@Transactional
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    public ClientServiceImpl() {
    }
    public ClientServiceImpl(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }
    public void setClientDao(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClient(Long id) {
        return clientDao.read(id);
    }
    @Override
    public boolean createTestClient() {
        clientDao.create(new Client());
        return true;
    }
    @Override
    public boolean createClient(Client client) {
        clientDao.create(client);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        clientDao.delete(clientDao.read(id));
        return true;
    }
    @Override
    public void updateClient(Client client) {
        clientDao.update(client);
    }
    @Override
    public List showClient() {
        return clientDao.findAll();
    }
}
