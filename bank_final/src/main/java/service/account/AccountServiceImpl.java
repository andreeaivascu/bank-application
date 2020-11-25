package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
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
    public Notification<Boolean> save(Account account) {
        /*return repository.save(account);*/

        Account accountBuild= new AccountBuilder()
                .setId(account.getId())
                .setIdentificationNumber(account.getIdentificationNumber())
                .setType(account.getType())
                .setAmountOfMoney(account.getAmountOfMoney())
                .setDateOfCreation(account.getDateOfCreation())
                .build();

        AccountValidator accountValidator = new AccountValidator(accountBuild);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> clientRegisterNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
        } else {

            clientRegisterNotification.setResult(repository.save(accountBuild));
        }
        return clientRegisterNotification;


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

    @Override
    public void update(Double amountOfMoney, Long id) {
        repository.update(amountOfMoney, id);
    }

    @Override
    public void processBill(Long id, Double payment) {

        repository.processBill(id, payment);

    }

    @Override
    public void transferMoney(Long idSend, Long idReceive, Double sum) {
        repository.transferMoney(idSend, idReceive, sum);
    }

}
