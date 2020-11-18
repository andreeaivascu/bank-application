package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {


    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

    void remove(Long id);

    //transfer money between 2 accounts
    boolean transferMoney(Long idSend, Long idReceive, Double sum);

    //process utilities bills
    boolean processBill(Long id, Double payment);
}
