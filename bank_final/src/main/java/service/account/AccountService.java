package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    Notification<Boolean> save(Account account);

    int getAgeOfAccount(Long id) throws EntityNotFoundException;

    void removeAll();

    void remove(Long id);

    void update(Double amountOfMoney, Long id);

    void processBill(Long id, Double payment);

    void transferMoney(Long idSend, Long idReceive, Double sum);
}