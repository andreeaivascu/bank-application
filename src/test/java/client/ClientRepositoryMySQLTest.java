package client;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryCacheDecorator;
import repository.client.ClientRepositoryMock;
import repository.client.ClientRepositoryMySQL;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ClientRepositoryMySQLTest {
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        clientRepository = new ClientRepositoryCacheDecorator(
                new ClientRepositoryMySQL(
                        new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                ),
                new Cache<>()
        );
    }


    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }


    Account account = new AccountBuilder()
            //.setId(1L)
            .setIdentificationNumber(123L)
            .setType("Type")
            .setAmountOfMoney(123D)
            .setDateOfCreation(new Date())
            .build();
      @Test
      public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                //.setId(1L)
                .setPnc(123L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1L)
                .setAddressClient("addressClient")
                //.setAccounts(Collections.singletonList(account))
                .setAccount(account)
                .build();
        clientRepository.save(client);
        clientRepository.save(client);
        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 3);
    }
/*
    @Test
    public void findById() throws Exception {

        Client client = new ClientBuilder()
                //.setId(2L)
                .setPnc(1433L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1L)
                .setAddressClient("addressClient")
                //.setAccounts(Collections.singletonList(account))
                .setAccount(account)
                .build();
        clientRepository.save(client);
       // assertEquals(client.getId(),2);
    }

   */
   @Test
    public void save() throws Exception {
        assertTrue(clientRepository.save(
                new ClientBuilder()
                        //.setId(1L)
                        .setPnc(123L)
                        .setNameClient("nameClient")
                        .setIdentityCardNumber(1L)
                        .setAddressClient("addressClient")
                        //.setAccounts(Collections.singletonList(account))
                        .setAccount(account)
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {
        clientRepository.removeAll();

    }

    @Test
    public void remove() throws Exception {
        clientRepository.remove(1L);

    }

}
