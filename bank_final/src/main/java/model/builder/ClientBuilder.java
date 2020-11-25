package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }


    public ClientBuilder setId(Long id) {
        client.setPnc(id);
        return this;
    }

    public ClientBuilder setPnc(Long pnc)
    {

        client.setPnc(pnc);
        return this;

    }


    public ClientBuilder setNameClient(String nameClient)
    {

        client.setNameClient(nameClient);
        return this;

    }
    public ClientBuilder setIdentityCardNumber(Long identityCardNumber)
    {

        client.setIdentityCardNumber(identityCardNumber);
        return this;

    }
    public ClientBuilder setAddressClient(String addressClient)
    {

        client.setAddressClient(addressClient);
        return this;

    }

    public ClientBuilder setAccount(Long account)
    {
        client.setAccount(account);
        return this;
    }

    public Client build() {
        return client;
    }

}
