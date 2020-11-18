package client;

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

import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class ClientRepositoryMockTest {
    private static ClientRepository repository;

    @BeforeClass
    public static void setupClass() {
        repository = new ClientRepositoryCacheDecorator(
                new ClientRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(repository.findAll().size() == 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
        repository.findById(1L);
    }
    Account account = new AccountBuilder()
            //.setId(1L)
            .setIdentificationNumber(123L)
            .setType("Type")
            .setAmountOfMoney(123D)
            .setDateOfCreation(new Date())
            .build();
    @Test
    public void save() throws Exception {
        Client client = new ClientBuilder()
                //.setId(1L)
                .setPnc(123L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1L)
                .setAddressClient("addressClient")
                .setAccount(account)
                .build();

        assertTrue(repository.save(client));
    }
}
