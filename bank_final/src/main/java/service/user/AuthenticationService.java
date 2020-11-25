package service.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.user.AuthenticationException;

import java.util.List;


public interface AuthenticationService {

    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);
   /*
    //delete a registration
    void deleteUser(Long id);


    //view all registrations
  //  User viewUsers(Long id) throws EntityNotFoundException;


    //find by id??
    User findById(Long id) throws EntityNotFoundException;


    //update information about user

    Notification<Boolean>updateUser(Long id, String valueToUpdate );*/



}
