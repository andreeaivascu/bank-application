package service.client;


import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService{

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Notification<Boolean> save(Client client) {
        /*return repository.save(client);*/
        Client clientBuild= new ClientBuilder()
        .setId(client.getId())
        .setPnc(client.getPnc())
        .setNameClient(client.getNameClient())
        .setIdentityCardNumber(client.getIdentityCardNumber())
        .setAddressClient(client.getAddressClient())
                .setAccount(client.getAccount())
        .build();

        ClientValidator clientValidator = new ClientValidator(clientBuild);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientRegisterNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
        } else {

            clientRegisterNotification.setResult(repository.save(clientBuild));
        }
        return clientRegisterNotification;
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }


    @Override
    public boolean remove(Long id) {
        return repository.remove(id);

    }

    @Override
    public void updateClient(Long id, String addressClient) {
        repository.updateClient(id,addressClient);

    }


}
