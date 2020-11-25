package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepositoryMock;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AccountServiceImplTest {

    private AccountService accountService;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(new AccountRepositoryMock());
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, accountService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
       accountService.findById(1L);
    }

    @Test
    public void save() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1234569891234567L)
                .setType("TypeUpdate")
                .setAmountOfMoney(125D)
                .setDateOfCreation(new Date())
                .build();

        Assert.assertTrue(
                accountService.save(account).getResult()

        );
    }

    @Test
    public void updateClient() throws Exception {
        Account account = new AccountBuilder()
                .setIdentificationNumber(1234569891234567L)
                .setType("TypeUpdate")
                .setAmountOfMoney(125D)
                .setDateOfCreation(new Date())
                .build();

        accountService.save(account);

        Double amountOfMoney=account.getAmountOfMoney();

        accountService.update(250D, account.getId());
        Double newAmountOfMoney=account.getAmountOfMoney();
        assertTrue(amountOfMoney!=newAmountOfMoney);
    }

}
