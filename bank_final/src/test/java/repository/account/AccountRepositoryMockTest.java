package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMock;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryMockTest {
    private static AccountRepository repository;

    @BeforeClass
    public static void setupClass() {
        repository = new AccountRepositoryCacheDecorator(
                new AccountRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {
        repository.removeAll();

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(repository.findAll().size() == 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById() throws Exception {

        Account account=repository.findById(1L);

        assertTrue( account!=null);
    }

    @Test
    public void save() throws Exception {
        Account account = new AccountBuilder()
                //.setId(1L)
                .setIdentificationNumber(1234567891234567L)
                .setType("Type")
                .setAmountOfMoney(0D)
                .setDateOfCreation(new Date())
                .build();

        assertTrue(repository.save(account));
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1234567891234569L)
                .setType("TypeFind")
                .setAmountOfMoney(122D)
                .setDateOfCreation(new Date())
                .build();
        repository.save(account);
        repository.save(account);
        repository.save(account);
        List<Account> accounts = repository.findAll();
        assertEquals(accounts.size(), 3);
    }


   /* @Test
    public void removeAll() throws Exception {

        repository.removeAll();
        List<Account> accounts = repository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void remove() throws Exception {

        repository.remove(2L);

    }*/

}
