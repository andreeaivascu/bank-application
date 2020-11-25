package repository.client;

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

    /*@Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
        Client client =repository.findById(1L);
        assertTrue(client!=null);
    }*/

    @Test
    public void save() throws Exception {
        Client client = new ClientBuilder()
                //.setId(1L)
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234567L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();

        assertTrue(repository.save(client));

    }

    @Test
    public void update() throws Exception {
        Client client = new ClientBuilder()
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234956L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();
        repository.save(client);

        String adresaVeche=client.getAddressClient();
        repository.updateClient(1L,"adresaNoua");
        //  account.setAmountOfMoney(230D);
        System.out.println(adresaVeche);
        String adresaNoua= repository.findById(1L).getAddressClient();
        System.out.println(adresaNoua);
        assertTrue(!(adresaVeche.equals(adresaNoua)));

    }
}
