package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;


public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    boolean save(User user);

    void removeAll();

    //remove an user
    void remove(Long id);

    //update an information about user (password)
    void update(Long id,String columnToUpdate, String valueToUpdate);

}
