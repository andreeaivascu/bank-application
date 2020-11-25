package controller;


import model.Account;
import model.Report;
import model.User;

import model.validation.Notification;
import repository.EntityNotFoundException;

import service.report.ReportService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdministratorView;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final UserService userService;
    //report
    private final ReportService reportService;




    public AdministratorController(AdministratorView administratorView, UserService userService,ReportService reportService) {
        this.administratorView = administratorView;
        this.userService =userService;

        //report
        this.reportService=reportService;

        administratorView.setCreateEmployeeButtonListener(new AdministratorController.CreateEmployeeButtonListener());
        administratorView.setUpdateAccountButtonListener(new AdministratorController.UpdateEmployeeButtonListener());
        administratorView.setDeleteEmployeeButtonListener(new AdministratorController.DeleteEmployeeButtonListener());
        administratorView.setViewEmployeeButtonListener(new AdministratorController.ViewEmployeeButtonListener());
        administratorView.setGenerateReportsButtonListener(new AdministratorController.GenerateReportsButtonListener());
        administratorView.setBtnGenerateAllOperationsButtonListener(new AdministratorController.GenerateAllOperationsButtonListener());
    }

    private class CreateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username=administratorView.getUsername();
            String password=administratorView.getPassword();

            Notification<Boolean> saveNotification = userService.save(username,password);
            if (saveNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), saveNotification.getFormattedErrors());
            } else {
                if (!saveNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Save user not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Save user successful!");
                }
            }

        }
    }

    private class DeleteEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Long id=Long.parseLong(administratorView.getId());


            User user=new User();

            try {
                user=userService.findById(id);

                userService.deleteUser(id);

                JOptionPane.showMessageDialog(administratorView.getContentPane(), "user was successfully deleted!");


            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "User not found!");

            }



        }
    }
    private class UpdateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Long id=Long.parseLong(administratorView.getId());
            String password=administratorView.getPassword();

            Notification<Boolean> updateNotification = userService.updateUser(id,password);
            if (updateNotification.hasErrors()) {
                System.out.println(password);
                JOptionPane.showMessageDialog(administratorView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                if (!updateNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Update employee not successful.");
                } else {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Update employee successful!");
                }
            }

        }
    }
    private class ViewEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



            Long idEmployee= Long.parseLong(administratorView.getId());

            try {
               User user= userService.findById(idEmployee);
                JOptionPane.showMessageDialog(administratorView.getContentPane(),

                        "Details about User:\n \n "+
                                "Id:   "+user.getId()+"\n "+
                                "Username:   "+user.getUsername()
                                );
            }
            catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "User not found!");
            }


        }
    }

    public class GenerateReportsButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            Long idEmployee=Long.parseLong(administratorView.getId());
            Long nrOfOperation=Long.parseLong(administratorView.getReport());

            List<Report> reports=reportService.findAll(idEmployee,nrOfOperation);
            System.out.println(reports.size());


             try {

                FileWriter myWriter = new FileWriter("UserReport.txt");
                 myWriter.write("id_employee: "+idEmployee+" "+"operations: ");
                for(Report report : reports)

                {
                    myWriter.write(report.getOperation()+",  ");



                }
                myWriter.close();
                System.out.println("Successfully wrote to the file UsersReport.txt.");
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "View report!");

            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }



        }
    }

    public class GenerateAllOperationsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Long idEmployee= Long.parseLong(administratorView.getId());
            try {
                Report report=reportService.identify(idEmployee);
                JOptionPane.showMessageDialog(administratorView.getContentPane(), report.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "User not found!");
            }


        }
    }
}
