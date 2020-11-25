package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository userRepository, RightsRolesRepository  rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    //delete an user
    @Override
    public boolean deleteUser(Long id) {
        //if(userRepository.findById)
        userRepository.remove(id);
        return false;
    }

    /*@Override
    public User viewUsers(Long id) throws EntityNotFoundException {
        return userRepository.findById(id);
    }*/


    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id);
    }


    //update information about user (password)
    @Override
    public Notification<Boolean> updateUser(Long id, String valueToUpdate) {


        List< User > users=userRepository.findAll();
        User findUser=new User();

           for( User user :users)
           {
               if(user.getId()==id)
               {
                   findUser=user;

               }
           }

        findUser.setPassword(valueToUpdate);
        System.out.println(findUser.getPassword());

        UserValidator userValidator = new UserValidator(findUser);
        boolean userValid = userValidator.validate();
        Notification<Boolean> updateRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(updateRegisterNotification::addError);
            updateRegisterNotification.setResult(Boolean.FALSE);
        } else {
            //findUser.setPassword(encodePassword(valueToUpdate));
            updateRegisterNotification.setResult(userRepository.update(id,encodePassword(valueToUpdate)));
        }
        return updateRegisterNotification;

       // userRepository.update(id, columnToUpdate, encodePassword(valueToUpdate));
    }

    @Override
    public Notification<Boolean> save(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }


    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
                // System.out.println(hexString.toString());
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
