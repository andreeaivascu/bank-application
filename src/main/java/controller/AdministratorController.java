package controller;

import model.Account;
import model.User;
import model.builder.AccountBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.AdministratorView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;

    public AdministratorController(AdministratorView administratorView, AuthenticationService authenticationService) {
        this.administratorView = administratorView;
        this.authenticationService =authenticationService;
        administratorView.setCreateEmployeeButtonListener(new AdministratorController.CreateEmployeeButtonListener());
        administratorView.setUpdateAccountButtonListener(new AdministratorController.UpdateEmployeeButtonListener());
        administratorView.setDeleteEmployeeButtonListener(new AdministratorController.DeleteEmployeeButtonListener());
        administratorView.setViewEmployeeButtonListener(new AdministratorController.ViewEmployeeButtonListener());
        administratorView.setGenerateReportsButtonListener(new AdministratorController.GenerateReportsButtonListener());
    }

    private class CreateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Long id=Long.parseLong(administratorView.getId());
            String username=administratorView.getUsername();
            String password=administratorView.getPassword();
           // String role=administratorView.getRoles();
            /*User user=new User();
            user.setUsername(username);
            user.setPassword(password);*/
            authenticationService.register(username,password);

            JOptionPane.showMessageDialog(administratorView.getContentPane(), "user was succesfully saved!");




        }
    }

    private class DeleteEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Long id=Long.parseLong(administratorView.getId());
            authenticationService.deleteUser(id);

            JOptionPane.showMessageDialog(administratorView.getContentPane(), "user was succesfully deleted!");


        }
    }
    private class UpdateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Long id=Long.parseLong(administratorView.getId());
            String password=administratorView.getPassword();
            //String password=administratorView.getPassword();
            authenticationService.updateUser(id, "columnToUpdate", password);
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "user was succesfully updated");

        }
    }
    private class ViewEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Check file UserView.txt!");

            List<User> users = authenticationService.viewUsers();


            try {
                FileWriter myWriter = new FileWriter("UserView.txt");
                for(User userOut : users)

                {
                    myWriter.write("id: "+userOut.getId()+" "+"username: "+userOut.getUsername()+",  ");

                    System.out.println(userOut.getUsername());

                }
                myWriter.close();
                System.out.println("Successfully wrote to the file UsersView.txt.");
            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }


        }
    }

    public class GenerateReportsButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
