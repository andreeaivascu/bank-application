package service.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface UserService {



    //delete a registration
    boolean deleteUser(Long id);


    //view all registrations
    //  User viewUsers(Long id) throws EntityNotFoundException;


    //find by id??
    User findById(Long id) throws EntityNotFoundException;

    Notification<Boolean> save(String username, String password);


    //update information about user

    Notification<Boolean> updateUser(Long id, String valueToUpdate );
}
