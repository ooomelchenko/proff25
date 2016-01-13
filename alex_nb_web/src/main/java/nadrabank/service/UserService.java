package nadrabank.service;

import nadrabank.domain.User;

import java.util.List;

/**
 * Created by HP on 10/19/2015.
 */
public interface UserService {
    User getUser(Long id);
    boolean createUser(User user);
    boolean delete(Long id);
    void updateUser(User user);
    List showUser();
    boolean isExist(String l, String p);

    User getByLogin(String login);
}
