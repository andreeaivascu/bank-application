import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;


public class ComponentFactory {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    //account----------------
    private final AccountService accountService;

    private final AccountRepository accountRepository;
    //-----------------

    //client------------

    private final ClientService clientService;

    private final ClientRepository clientRepository;
    //-----------------


    private final RightsRolesRepository rightsRolesRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);

        //account---
        this.accountRepository=new AccountRepositoryMySQL(connection);
        this.accountService=new AccountServiceImpl(accountRepository);
        //------

        //client---
        this.clientRepository=new ClientRepositoryMySQL(connection);
        this.clientService=new ClientServiceImpl(clientRepository);
        //------
    }


    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }


    //account----
    public AccountService getAccountService() {
        return accountService;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }
    //---------



    //client----
    public ClientService getClientService() {
        return clientService;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }
    //---------

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
}
