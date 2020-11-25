package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame{

    private JTextField tfIdAccount;
    private JTextField tfIdentificationNumber;
    private JTextField tfTypeAccount;
    private JTextField tfAmountOfMoney;
    private JTextField tfDateOfCreation;

    private JTextField tfProcessBill;
    private JTextField tfTransferMoney;


    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccount;
    private JButton btnTransferMoney;
    private JButton btnProcessUtilitiesBills;

    private JTextField tfIdClient;
    private JTextField tfPnc;
    private JTextField tfNameClient;
    private JTextField tfIdentityCardNumber;
    private JTextField tfAddressClient;
    private JTextField tfAccount;

    private JButton btnAddClientInformation;
    private JButton btnUpdateClient;
    private JButton btnViewClient;

    public EmployeeView() throws HeadlessException {
        setSize(500, 900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfIdAccount);
        add(tfIdentificationNumber);
        add(tfTypeAccount);
        add(tfAmountOfMoney);
        add(tfDateOfCreation);
        add(tfProcessBill);
        add(tfTransferMoney);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
        add(btnViewAccount);
        add(btnTransferMoney);
        add(btnProcessUtilitiesBills);


        add(tfIdClient);
        add(tfPnc);
        add(tfNameClient);
        add(tfIdentityCardNumber);
        add(tfAddressClient);
        add(tfAccount);

        add(btnAddClientInformation);
        add(btnUpdateClient);
        add(btnViewClient);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }

    private void initializeFields() {
        tfIdAccount = new JTextField("enter idAccount");
        tfIdentificationNumber = new JTextField("*enter identificationNumber");
        tfTypeAccount = new JTextField("*enter typeAccount");
        tfAmountOfMoney = new JTextField("*enter amountOfMoney");
        tfDateOfCreation = new JTextField("*enter dateOfCreation (ex: DD-MM-YY)");
        tfProcessBill = new JTextField("ProcessBill: enter nameOfBill:sum of money");
        tfTransferMoney = new JTextField("TransferMoney: enter idSender-idReceiver-sum");
        btnCreateAccount = new JButton("Create account");
        btnUpdateAccount = new JButton("Update account");
        btnDeleteAccount = new JButton("Delete account");
        btnViewAccount = new JButton("View account");
        btnTransferMoney= new JButton("Transfer money");
        btnProcessUtilitiesBills = new JButton("Process bill");


        btnAddClientInformation = new JButton("Add client information");
        btnUpdateClient=new JButton("Update client");
        btnViewClient=new JButton("View client");
        tfIdClient=new JTextField("enter idClient");
        tfPnc=new JTextField("*enter PNC");
        tfNameClient=new JTextField("*enter name");
        tfIdentityCardNumber=new JTextField("*enter identityCardNumber");
        tfAddressClient= new JTextField("*enter address");
        tfAccount= new JTextField("*enter account");
    }


    //account
    public String getIdAccount() {
        return tfIdAccount.getText();
    }

    public String getIdentificationNumber() {
        return tfIdentificationNumber.getText();
    }

    public String getTypeAccount(){
        return tfTypeAccount.getText();
    }

    public String getAmountOfMoney() {
        return tfAmountOfMoney.getText();
    }

    public String getDateOfCreation() {
        return tfDateOfCreation.getText();
    }

    public String getProcessBill() {
        return tfProcessBill.getText();
    }

    public String getTransferMoney() {
        return tfTransferMoney.getText();
    }


    //client
    public String getIdClient() {
        return tfIdClient.getText();
    }

    public String getPnc() {
        return tfPnc.getText();
    }

    public String getNameClient() {
        return tfNameClient.getText();
    }

    public String getIdentityCardNumber() {
        return tfIdentityCardNumber.getText();
    }

    public String getAddressClient() {
        return tfAddressClient.getText();
    }

    public String getAccount() {
        return tfAccount.getText();
    }






   //account
    public void setCreateAccountButtonListener(ActionListener createButtonListener) {
        btnCreateAccount.addActionListener(createButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteButtonListener) {
        btnDeleteAccount.addActionListener(deleteButtonListener);
    }
    public void setViewAccountButtonListener(ActionListener viewAccountButtonListener) {
        btnViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void setTransferMoneyButtonListener(ActionListener transferMoneyButtonListener) {
        btnTransferMoney.addActionListener(transferMoneyButtonListener);
    }
    public void setProcessUtilitiesBillsButtonListener(ActionListener processUtilitiesBillsButtonListener) {
        btnProcessUtilitiesBills.addActionListener(processUtilitiesBillsButtonListener);
    }

    //client
    public void setAddClientInformationButtonListener(ActionListener informationButtonListener) {
        btnAddClientInformation.addActionListener(informationButtonListener);
    }

    public void setUpdateClient(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setViewClient(ActionListener viewClientButtonListener) {
        btnViewClient.addActionListener(viewClientButtonListener);
    }

}
