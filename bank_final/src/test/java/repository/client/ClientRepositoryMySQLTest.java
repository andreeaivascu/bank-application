package repository.client;

import database.DBConnectionFactory;

import model.Client;

import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;

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

   @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();

        assertEquals(clients.size(), 0);
    }


    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                //.setId(1L)
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234567L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();
        clientRepository.save(client);
        System.out.println(clientRepository.findAll().size());

        List<Client>clients = clientRepository.findAll();
        assertEquals(clients.size(), 8); //8
    }

    @Test
    public void findById() throws Exception {

        Client client = new ClientBuilder()
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234956L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();
        clientRepository.save(client);
        Client clientFound=clientRepository.findById(1L);
        assertTrue(clientFound!=null);
    }


   @Test
    public void save() throws Exception {
        assertTrue(clientRepository.save(
                new ClientBuilder()
                         //.setId(1L)
                        .setPnc(1234567891234L)
                        .setNameClient("nume client")
                        .setIdentityCardNumber(1234567891234956L)
                        .setAddressClient("addressa client ")
                        .setAccount(1L)
                        .build()
        ));
       System.out.println(clientRepository.findAll().size());
    }

  /*  @Test
    public void removeAll() throws Exception {
        clientRepository.removeAll();
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);

    }

    @Test
    public void remove() throws Exception {
        assertTrue(clientRepository.remove(1L));

    }*/

    @Test
    public void update() throws Exception {
        Client client = new ClientBuilder()
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234956L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();


        clientRepository.save(client);

        String adresaVeche=client.getAddressClient();
        clientRepository.updateClient(1L,"adresaNoua");
      //  account.setAmountOfMoney(230D);
        System.out.println(adresaVeche);
        String adresaNoua= clientRepository.findById(1L).getAddressClient();
        System.out.println(adresaNoua);
        assertTrue(!(adresaVeche.equals(adresaNoua)));

    }




}
