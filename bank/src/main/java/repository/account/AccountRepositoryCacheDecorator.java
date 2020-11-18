package repository.account;

import model.Account;
import repository.Cache;
import repository.EntityNotFoundException;

import java.util.List;

public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator{

    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Account> accounts = decoratedRepository.findAll();
        cache.save(accounts);
        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Account account) {
        cache.invalidateCache();
        return decoratedRepository.save(account);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public void remove(Long id) {
        cache.invalidateCache();
        decoratedRepository.remove(id);
    }

    //transfer money between 2 accounts
    @Override
    public boolean transferMoney(Long idSend, Long idReceive, Double sum) {
        cache.invalidateCache();
        return decoratedRepository.transferMoney(idSend, idReceive, sum);
    }

    @Override
    public boolean processBill(Long id, Double payment) {
        cache.invalidateCache();
        return decoratedRepository.processBill(id,payment);
    }
}

