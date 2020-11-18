package service.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    int getAgeOfAccount(Long id) throws EntityNotFoundException;

    void removeAll();

    void remove(Long id);
}