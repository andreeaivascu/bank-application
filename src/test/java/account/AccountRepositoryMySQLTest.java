package account;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMySQL;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        accountRepository = new AccountRepositoryCacheDecorator(
                new AccountRepositoryMySQL(
                        new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                ),
                new Cache<>()
        );
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

   @Test
    public void remove(){
        accountRepository.remove(1L);
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
               // .setId(1L)
                .setIdentificationNumber(123L)
                .setType("Type")
                .setAmountOfMoney(123D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);
        accountRepository.save(account);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void findById() throws Exception {

      /* Account account = new AccountBuilder()
                 .setId(1L)
                .setIdentificationNumber(123L)
                .setType("Type")
                .setAmountOfMoney(123D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);
        assertEquals(accountRepository.findById(1L),1);*/

    }

    @Test
    public void save() throws Exception {
        assertTrue(accountRepository.save(
                new AccountBuilder()
                        //.setId(1L)
                        .setIdentificationNumber(123L)
                        .setType("Type")
                        .setAmountOfMoney(123D)
                        .setDateOfCreation(new Date())
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {
        accountRepository.removeAll();
        //assertTrue(accountRepository==null);




    }
}