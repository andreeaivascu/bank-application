package service.user;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;

import java.util.List;


public interface AuthenticationService {

    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);

    //delete a registration
    void deleteUser(Long id);


    //view all registrations
    List<User> viewUsers();


    //update information about user

    void updateUser(Long id, String columnToUpdate, String valueToUpdate );

}
