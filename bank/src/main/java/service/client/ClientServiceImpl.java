package service.client;


import model.Client;
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
    public boolean save(Client client) {
        return repository.save(client);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }


    @Override
    public void remove(Long id) {
        repository.remove(id);
    }



}
