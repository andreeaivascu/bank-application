package service.client;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepositoryMock;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ClientServiceImplTest {

    private ClientService clientService;

    @Before
    public void setup() {
        clientService = new ClientServiceImpl(new ClientRepositoryMock());
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, clientService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
       Client client= clientService.findById(1L);
       assertTrue(client!=null);
    }

    @Test
    public void save() throws Exception {
        Client client = new ClientBuilder()
                .setId(1L)
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234956L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();

        Assert.assertTrue(
                clientService.save(client).getResult()

        );

    }


    @Test
    public void updateClient() throws Exception {
        Client client = new ClientBuilder()
                //.setId(2L)
                .setPnc(1234567891234L)
                .setNameClient("nameClient")
                .setIdentityCardNumber(1234567891234956L)
                .setAddressClient("addressClient")
                .setAccount(1L)
                .build();

        clientService.save(client);

        String adresaVeche=client.getAddressClient();
       // System.out.println(adresaVeche);
        clientService.updateClient(client.getId(),"adresaNoua");
        String adresaNoua=client.getAddressClient();
        //System.out.println(adresaNoua);
        assertTrue(adresaNoua.equals(adresaVeche)!=true);
    }

}
