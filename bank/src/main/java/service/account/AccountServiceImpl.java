package service.account;

import model.Account;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public boolean save(Account account) {
        return repository.save(account);
    }

    @Override
    public int getAgeOfAccount(Long id) throws EntityNotFoundException {
        Account account = findById(id);
        Date publishedDate = account.getDateOfCreation();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publishedDate);
        int yearOfPublishing = calendar.get(Calendar.YEAR);
        calendar.setTime(new Date());
        int yearToday = calendar.get(Calendar.YEAR);

        return yearToday - yearOfPublishing;
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
