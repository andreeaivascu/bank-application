package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;


public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
   // private JButton btnRegister;
    private JButton btnLogout;

    public LoginView() throws HeadlessException {
        setSize(400, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnLogin);
       // add(btnRegister);
        add(btnLogout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField("enter username");
        tfPassword = new JTextField("enter password");
        btnLogin = new JButton("Login");
        btnLogout = new JButton("Logout");
        //btnRegister = new JButton("Register");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setUsername() {

        tfUsername.setText("enter username");
    }

    public void setPassword() {

        tfPassword.setText("enter password");
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener)
    {
        btnLogout.addActionListener(logoutButtonListener);
    }

   // public void setRegisterButtonListener(ActionListener registerButtonListener) {
       // btnRegister.addActionListener(registerButtonListener);
   // }

}
