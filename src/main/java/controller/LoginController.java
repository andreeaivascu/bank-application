package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.security.RightsRolesRepository;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import service.account.AccountService;
import service.client.ClientService;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;


public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final RightsRolesRepository rightsRolesRepository;
    private final UserRepository userRepository;
    //account
    private final AccountService accountService;
    //client
    private final ClientService clientService;



    public LoginController(LoginView loginView, AuthenticationService authenticationService, RightsRolesRepository rightsRolesRepository,UserRepository userRepository, AccountService accountService, ClientService clientService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.rightsRolesRepository=rightsRolesRepository;
        this.userRepository=userRepository;
        this.accountService = accountService;
        this.clientService = clientService;

        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

           /* List<User> allUsers=userRepository.findAll();
            Role employeeRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
            allUsers.get(0).setRoles(Collections.singletonList(employeeRole));
            for(User user : allUsers)
            System.out.println(user.getUsername()+" "+user.getRoles());*/

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);

            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());


                } else {

                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    EmployeeView employeeView=new EmployeeView();
                    EmployeeController employeeController=new EmployeeController(employeeView,rightsRolesRepository, accountService, clientService);

                    //valabil doar pentru administrator
                    if(username.equals("admin@yahoo.com"))

                    {
                        AdministratorView administratorView = new AdministratorView();
                        AdministratorController administratorController = new AdministratorController(administratorView, authenticationService);
                    }



                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }


}
