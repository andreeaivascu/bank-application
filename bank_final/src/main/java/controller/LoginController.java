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
import service.report.ReportService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
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
    //user
    private final UserService userService;

    //reports
    private  final ReportService reportService;

    private AdministratorView administratorView ;
    private EmployeeView employeeView;



    public LoginController(LoginView loginView, AuthenticationService authenticationService, RightsRolesRepository rightsRolesRepository,UserRepository userRepository, AccountService accountService, ClientService clientService,UserService userService, ReportService reportService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.rightsRolesRepository=rightsRolesRepository;
        this.userRepository=userRepository;
        this.accountService = accountService;
        this.clientService = clientService;
        this.userService=userService;

        //reports

        this.reportService=reportService;

        loginView.setLoginButtonListener(new LoginButtonListener());
       // loginView.setRegisterButtonListener(new RegisterButtonListener());
        loginView.setLogoutButtonListener(new LogoutButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            List<User> allUsers=userRepository.findAll();
            //Role employeeRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
           // allUsers.get(0).setRoles(Collections.singletonList(employeeRole));
           /* for(User user : allUsers)
                System.out.println(user.getUsername()+" "+Collections.singletonList(rightsRolesRepository.findRolesForUser(user.getId())));
            //System.out.println(user.getUsername()+" "+Collections.singletonList(rightsRolesRepository.findRolesForUser(user.getId())));

            System.out.println((Collections.singletonList(rightsRolesRepository.findRolesForUser(1L))).equals(ADMINISTRATOR));

            System.out.println(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR));
            System.out.println(rightsRolesRepository.findRoleByTitle(EMPLOYEE));

             Role role = (Role)rightsRolesRepository.findRoleByTitle(EMPLOYEE);
              System.out.println(role.getId());


            Role role2 = (Role)rightsRolesRepository.findRolesForUser(1L).get(0);
            System.out.println(role2.getId());*/



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


                    Long idAcces=null;
                    for(User user : allUsers) {
                        if (user.getUsername().equals(username))
                        {
                            idAcces=user.getId();
                        }

                    }
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                   // EmployeeView employeeView=new EmployeeView();
                    employeeView=new EmployeeView();
                    EmployeeController employeeController=new EmployeeController(employeeView,rightsRolesRepository, accountService, clientService, reportService,idAcces);

                     //ArrayList<String> listaActiuniEmployee= employeeController.getListaActiuni();
                     //report.put(idAcces,listaActiuniEmployee);
                    //report=employeeController.getReport();
                    System.out.println(idAcces);






                    //valabil doar pentru administrator
                    //if(username.equals("admin@yahoo.com"))

                    //if((Collections.singletonList(rightsRolesRepository.findRolesForUser(1L))).equals(ADMINISTRATOR))
                    Long idUser=null;
                    for(User user : allUsers)
                    {
                        if (user.getUsername().equals(username))
                        {
                            idUser = user.getId();
                            Role roleUser = (Role)rightsRolesRepository.findRolesForUser(idUser).get(0);
                            //System.out.println(roleUser.getId());
                            Role roleADMIN = (Role)rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);


                        if(roleUser.getId()== roleADMIN.getId())

                          {
                             // AdministratorView administratorView = new AdministratorView();
                              administratorView = new AdministratorView();
                              AdministratorController administratorController = new AdministratorController(administratorView, userService, reportService);

                          }

                       }

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

    private class LogoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           loginView.setUsername();
           loginView.setPassword();
           employeeView.setVisible(false);
           administratorView.setVisible(false);



        }
    }




}
