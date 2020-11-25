package controller;

import model.Account;

import model.Client;


import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;

import service.account.AccountService;
import service.client.ClientService;
import service.report.ReportService;
import view.EmployeeView;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.*;


public class EmployeeController {
    private final EmployeeView employeeView;
    private final RightsRolesRepository rightsRolesRepository;
    //account--------
    private final AccountService accountService;
    //client---------
    private final ClientService clientService;


    //report
    private final ReportService reportService;

    //idEmployee
    private Long idEmployee;




    public EmployeeController( EmployeeView employeeView, RightsRolesRepository rightsRolesRepository, AccountService accountService, ClientService clientService, ReportService reportService,Long idEmployee) {
        this.employeeView = employeeView;
        this.rightsRolesRepository = rightsRolesRepository;
        this.accountService=accountService;
        this.clientService=clientService;


        //report
        this.reportService=reportService;
        this.idEmployee=idEmployee;


        employeeView.setCreateAccountButtonListener(new CreateAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setViewAccountButtonListener(new ViewAccountButtonListener());
        employeeView.setTransferMoneyButtonListener(new TransferMoneyButtonListener());
        employeeView.setProcessUtilitiesBillsButtonListener(new ProcessUtilitiesBillsButtonListener());
        employeeView.setAddClientInformationButtonListener(new AddClientInformationButtonListener());
        employeeView.setUpdateClient(new UpdateClientButtonListener());
        employeeView.setViewClient(new ViewClientButtonListener());
    }

    private class CreateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long identificationNumber = Long.parseLong(employeeView.getIdentificationNumber());
            String type=employeeView.getTypeAccount();
            Double amountOfMoney= Double.parseDouble(employeeView.getAmountOfMoney());
            String dateOfCreation=employeeView.getDateOfCreation();

            String[] stringData = dateOfCreation.split("-");

            List<String> data=new ArrayList<>();


            for (String t : stringData) {
                data.add(t);
            }


            Calendar c1 = Calendar.getInstance();

            c1.set(Calendar.MONTH, Integer.parseInt(data.get(1)));

            c1.set(Calendar.DATE, Integer.parseInt(data.get(0)));

            c1.set(Calendar.YEAR, Integer.parseInt(data.get(2)));

            Date dateOne = c1.getTime();

            Instant inst = dateOne.toInstant();

            System.out.println(
                    "Date: " + dateOne.from(inst));

            Account account= new  Account();

                  account.setIdentificationNumber(identificationNumber);
                  account.setType(type);
                  account .setAmountOfMoney(amountOfMoney);
                  account.setDateOfCreation(dateOne.from(inst));



                  reportService.save(idEmployee,"saveAccount");
                 // System.out.println(idEmployee+"saveAccount");


            Notification<Boolean> saveNotification = accountService.save(account);
            if (saveNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), saveNotification.getFormattedErrors());
            } else {
                if (!saveNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Save account not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Save account successful!");
                }
            }




        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
       public void actionPerformed(ActionEvent e) {
            Long idAccount=Long.parseLong(employeeView.getIdAccount());
            Account account=new Account();

            try {
                account=accountService.findById(idAccount);
                accountService.remove(idAccount);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account was successfully deleted!");
                reportService.save(idEmployee,"deleteAccount");

            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account not found!");

            }



            //accountService.remove(idAccount);
           // JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account was successfully deleted!");


           // System.out.println(report);

        }
    }
    private class UpdateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long idAccount=Long.parseLong(employeeView.getIdAccount());
            Double amountOfMoney = Double.parseDouble(employeeView.getAmountOfMoney());

            Account account=new Account();

            try {
                account=accountService.findById(idAccount);
                accountService.update(amountOfMoney, idAccount);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account was successfully updated!");
                reportService.save(idEmployee,"updateAccount");

            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account not found!");

            }

        }
    }
    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long idAccount = Long.parseLong(employeeView.getIdAccount());

            try {
                Account accountView= accountService.findById(idAccount);
                JOptionPane.showMessageDialog(employeeView.getContentPane(),

                                "Details about account:\n \n "+
                                        "Type:   "+accountView.getType()+"\n "+
                                "AmountOfMoney:   "+accountView.getAmountOfMoney()+"\n "+
                                "IdentificationNumber:   "+accountView.getIdentificationNumber()+"\n "+
                                "DateOfCreation:   "+accountView.getDateOfCreation());
                reportService.save(idEmployee,"viewAccount");
            }
            catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account not found!");
            }


        }
    }

    private class TransferMoneyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            String[] getString  = employeeView.getTransferMoney().split("-");

            List<String> idSenderReceiverSum =new ArrayList<>();


            for (String t : getString) {
                idSenderReceiverSum.add(t);
            }


            Long idSender=Long.parseLong(idSenderReceiverSum.get(0));
            Long idReceiver=Long.parseLong(idSenderReceiverSum.get(1));
            Double sum=Double.parseDouble(idSenderReceiverSum.get(2));


            Account accountSender=new Account();
            Account accountReceiver=new Account();


            try { accountSender=accountService.findById(idSender);

                if(accountSender.getAmountOfMoney()<=sum)

                {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),"Insufficient money!!");

                }
                else {

                    accountService.transferMoney(idSender,idReceiver,sum);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),"Successfully transfer!!");
                    reportService.save(idEmployee,"transferMoney");


                }



            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Invalid accountSender!!");

            }

            try { accountReceiver=accountService.findById(idReceiver);


            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Invalid accountReceiver!!");

            }




        }
    }


    private class ProcessUtilitiesBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long idAccount=Long.parseLong(employeeView.getIdAccount());


            String[] getString  = employeeView.getProcessBill().split(":");

            List<String> nameOfBillAndSum =new ArrayList<>();


            for (String t : getString) {
                nameOfBillAndSum.add(t);
            }

            Double payment =Double.parseDouble(nameOfBillAndSum.get(1));

            Account account=new Account();

            try { account=accountService.findById(idAccount);

                if(account.getAmountOfMoney()<payment)

                {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),"Insufficient money!!");

                }
                else {

                    accountService.processBill(idAccount, payment);

                    JOptionPane.showMessageDialog(employeeView.getContentPane(),

                            "Details of Payment:\n \n " +
                                    nameOfBillAndSum.get(0) + "\n " +
                                    nameOfBillAndSum.get(1) + "\n "
                    );
                    reportService.save(idEmployee,"processBill");
                }


            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Account not found!!");
            }




        }
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long idClient = Long.parseLong(employeeView.getIdClient());

            String addressClient = employeeView.getAddressClient();
            clientService.updateClient(idClient,addressClient) ;


            Client client=new Client();

            try { client=clientService.findById(idClient);

                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account was successfully updated!");
                reportService.save(idEmployee,"updateClient");

            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Client not found!!");

            }



        }
    }

    private class ViewClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long idClient = Long.parseLong(employeeView.getIdClient());

            Client client=new Client();

            try {
                client= clientService.findById(idClient);
                JOptionPane.showMessageDialog(employeeView.getContentPane(),

                        "Details about client:\n \n "+
                                "PNC:   "+client.getPnc()+"\n "+
                                "NameClient:   "+client.getNameClient()+"\n "+
                                "IdentityCardNumber:   "+client.getIdentityCardNumber()+"\n "+
                                "AddressClient:   "+client.getAddressClient()+"\n"+
                                "Account: "+client.getAccount());
                reportService.save(idEmployee,"viewClient");
            }
            catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Client not found!!");
            }




        }
    }
    private class AddClientInformationButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long pnc = Long.parseLong(employeeView.getPnc());
            String nameClient=employeeView.getNameClient();
            Long identityCardNumber= Long.parseLong(employeeView.getIdentityCardNumber());
            Long idAccount= Long.parseLong(employeeView.getAccount());
            String addressClient=employeeView.getAddressClient();


            Client client= new Client();
            client.setPnc(pnc);
            client.setNameClient(nameClient);
            client.setIdentityCardNumber(identityCardNumber);
            client.setAddressClient(addressClient);
            client.setAccount(idAccount);


            Notification<Boolean> saveNotification = clientService.save(client);
            if (saveNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), saveNotification.getFormattedErrors());
            } else {
                if (!saveNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Save client not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Save client successful!");
                    reportService.save(idEmployee,"addClient");
                }
            }



        }
    }

}
