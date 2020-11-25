package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
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

    /*@Before
    public void cleanUp() {
        accountRepository.removeAll();
    }*/


   @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 1);//0
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1234567891234569L)
                .setType("TypeFind")
                .setAmountOfMoney(122D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);
        accountRepository.save(account);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 3); //8
    }

    @Test
    public void save() throws Exception {
        assertTrue(accountRepository.save(
                new AccountBuilder()
                        .setIdentificationNumber(1224567891234545L)
                        .setType("TypeSave")
                        .setAmountOfMoney(123D)
                        .setDateOfCreation(new Date())
                        .build()
        ));
    }

    @Test
    public void findById() throws Exception {

       Account account = new AccountBuilder()
                .setIdentificationNumber(1234567891234567L)
                .setType("TypeFindByID")
                .setAmountOfMoney(124D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);
        Account accountFound=accountRepository.findById(2L);
        assertTrue(accountFound!=null);

    }




    @Test
    public void transferMoney() throws Exception {
        Account accountSender = new AccountBuilder()
                .setIdentificationNumber(1244569891234567L)
                .setType("TypeTransfer1")
                .setAmountOfMoney(126D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(accountSender);


        Account accountReceiver = new AccountBuilder()
                .setIdentificationNumber(1244569891234567L)
                .setType("TypeTransfer2")
                .setAmountOfMoney(127D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(accountReceiver);


        accountRepository.transferMoney(1L,2L,1D);


        assertTrue((accountRepository.findById(1L).getAmountOfMoney()==125) && (accountRepository.findById(2L).getAmountOfMoney()==128));

    }

    @Test
    public void processBill() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1244569891234567L)
                .setType("TypeProcessBill")
                .setAmountOfMoney(127D)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);

        accountRepository.processBill(6L,1D);


        assertTrue(accountRepository.findById(6L).getAmountOfMoney()==126 );

    }

    @Test
    public void update() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1234569891234567L)
                .setType("TypeUpdate")
                .setAmountOfMoney(125D)
                .setDateOfCreation(new Date())
                .build();


        accountRepository.save(account);

        double sumaVeche= account.getAmountOfMoney();
        accountRepository.update(230D,3L);//1


        assertTrue(sumaVeche!=(accountRepository.findById(3L).getAmountOfMoney()));

    }



   @Test
    public void removeAll() throws Exception {
        accountRepository.removeAll();
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);

    }

    @Test
    public void remove() throws Exception {
        assertTrue(accountRepository.remove(1L));

    }


}