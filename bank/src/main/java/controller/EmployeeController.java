package controller;

import model.Account;

import model.Client;
import model.builder.AccountBuilder;

import repository.account.AccountRepository;
import repository.security.RightsRolesRepository;

import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.*;


public class EmployeeController {
    private final EmployeeView employeeView;
    private final RightsRolesRepository rightsRolesRepository;
    //account--------
    private final AccountService accountService;
    //client
    private final ClientService clientService;


    public EmployeeController(EmployeeView employeeView, RightsRolesRepository rightsRolesRepository, AccountService accountService, ClientService clientService) {
        this.employeeView = employeeView;
        this.rightsRolesRepository = rightsRolesRepository;
        this.accountService=accountService;
        this.clientService=clientService;

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

            String[] tokens = dateOfCreation.split("-");

            List<String> data=new ArrayList<>();


            for (String t : tokens) {
                data.add(t);
            }

           /* for(int i=0;i<data.size();i++)
            {
                System.out.println(data.get(i));

            }*/

           // Right right= rightsRolesRepository.findRightByTitle(ADD_CLIENT);

            /*AccountBuilder builderAccount= new AccountBuilder();
            Account account=builderAccount.build();*/

            Calendar c1 = Calendar.getInstance();

            // Set Month
            // MONTH starts with 0 i.e. ( 0 - Jan)
            c1.set(Calendar.MONTH, Integer.parseInt(data.get(1)));

            // Set Date
            c1.set(Calendar.DATE, Integer.parseInt(data.get(0)));

            // Set Year
            c1.set(Calendar.YEAR, Integer.parseInt(data.get(2)));

            // Creating a date object
            // with specified time.
            Date dateOne = c1.getTime();

            Instant inst = dateOne.toInstant();

            System.out.println(
                    "Date: " + dateOne.from(inst));

            Account account= new  Account();

                  account.setIdentificationNumber(identificationNumber);
                    account.setType(type);
                   account .setAmountOfMoney(amountOfMoney);
                    account.setDateOfCreation(dateOne.from(inst));


            accountService.save(account);



        }
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
       public void actionPerformed(ActionEvent e) {
           /* Long id=Long.parseLong(employeeView.getId());
            accountService.removeAll(id);*/




        }
    }
    private class UpdateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }
    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }

    private class TransferMoneyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }


    private class ProcessUtilitiesBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }

    private class ViewClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



        }
    }
    private class AddClientInformationButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long pnc = Long.parseLong(employeeView.getPnc());
            String nameClient=employeeView.getNameClient();
            Long identityCardNumber= Long.parseLong(employeeView.getIdentityCardNumber());
            String addressClient=employeeView.getAddressClient();
            //Long account= Long.parseLong(employeeView.getAccount());

            Client client= new Client();
            client.setPnc(pnc);
            client.setNameClient(nameClient);
            client.setIdentityCardNumber(identityCardNumber);
            client.setAddressClient(addressClient);
            //client.setAccount(account);

            clientService.save(client);



        }
    }



}
