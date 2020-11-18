package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {

    private JTextField tfId;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfRoles;

    private JButton btnCreateEmployee;
    private JButton btnViewEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnGenerateReports;

    public AdministratorView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfId);
        add(tfUsername);
        add(tfPassword);
        add(tfRoles);
        add(btnCreateEmployee);
        add(btnViewEmployee);
        add(btnUpdateEmployee);
        add(btnDeleteEmployee);
        add(btnGenerateReports);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfId = new JTextField("enter id");
        tfUsername= new JTextField("enter username");
        tfPassword = new JTextField("enter password");
        tfRoles = new JTextField("enter roles");
        btnCreateEmployee = new JButton("Create employee");
        btnViewEmployee = new JButton("View employee");
        btnUpdateEmployee = new JButton("Update employee");
        btnDeleteEmployee = new JButton("Delete employee");
        btnGenerateReports=new JButton("Generate Reports");
    }

    public String getId() {
        return tfId.getText();
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword(){
        return tfPassword.getText();
    }

    public String getRoles() {
        return tfRoles.getText();
    }


    public void setCreateEmployeeButtonListener(ActionListener createButtonListener) {
        btnCreateEmployee.addActionListener(createButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateButtonListener) {
        btnUpdateEmployee.addActionListener(updateButtonListener);
    }

    public void setDeleteEmployeeButtonListener(ActionListener deleteButtonListener) {
        btnDeleteEmployee.addActionListener(deleteButtonListener);
    }
    public void setViewEmployeeButtonListener(ActionListener viewButtonListener) {
        btnViewEmployee.addActionListener(viewButtonListener);
    }
    public void setGenerateReportsButtonListener(ActionListener generateReportsButtonListener) {
        btnGenerateReports.addActionListener(generateReportsButtonListener);
    }



}
