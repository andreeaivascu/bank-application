package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepositoryMock implements AccountRepository{
    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts = new ArrayList<>();
    }

    public List<Account> findAll() {
        return accounts;
    }

    public Account findById(Long id) throws EntityNotFoundException {
        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if (filteredAccounts.size() > 0) {
            return filteredAccounts.get(0);
        }
        throw new EntityNotFoundException(id, Account.class.getSimpleName());
    }

    public boolean save(Account account) {
        return accounts.add(account);
    }

    @Override
    public void removeAll() {
        accounts.clear();
    }

    @Override
    public boolean remove(Long id) {
        return accounts.remove(id);
    }

    @Override
    public void update(Double amountOfMoney, Long id) {
        String s=""+id;
        int idAccount= Integer.parseInt(s);
        accounts.get(idAccount).setAmountOfMoney(amountOfMoney);
    }


    //transfer money between 2 accounts
    @Override
    public void transferMoney(Long idSend, Long idReceive, Double sum)  {


      /*  List<Account> filteredAccountsSend = accounts.parallelStream()
                .filter(it -> it.getId().equals(idSend))
                .collect(Collectors.toList());
        List<Account> filteredAccountsReceive = accounts.parallelStream()
                .filter(it -> it.getId().equals(idReceive))
                .collect(Collectors.toList());
        if (filteredAccountsSend.size() > 0 && filteredAccountsReceive.size() > 0 )
        {
           Account accountSend=filteredAccountsSend.get(0);
           Account accountReceive=filteredAccountsReceive.get(0);

           accountSend.setAmountOfMoney(accountSend.getAmountOfMoney()-sum);
           accountReceive.setAmountOfMoney(accountReceive.getAmountOfMoney()+sum);

        //filteredAccountsSend.parallelStream().mapToDouble(num-> num.getAmountOfMoney()-sum);
        //filteredAccountsReceive.parallelStream().mapToDouble(num-> num.getAmountOfMoney()+sum);

           return true;

          }

         return false;*/
        String stringSend=""+idSend;
        String stringReceive=""+idReceive;
        int idAccountSend= Integer.parseInt(stringSend);
        int idAccountReceive= Integer.parseInt(stringReceive);

        accounts.get(idAccountSend).setAmountOfMoney(accounts.get(idAccountSend).getAmountOfMoney()-sum);
        accounts.get(idAccountReceive).setAmountOfMoney(accounts.get(idAccountReceive).getAmountOfMoney()+sum);


    }

    @Override
    public void processBill(Long id, Double payment) {

        /*List<Account> filteredAccountsSend = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if(filteredAccountsSend.size()>0) {
            Account accountPayment = filteredAccountsSend.get(0);
            accountPayment.setAmountOfMoney(accountPayment.getAmountOfMoney() - payment);
            return true;
            //filteredAccountsSend.parallelStream().mapToDouble(num-> num.getAmountOfMoney()-payment);
        }
        return false;*/
         String s=""+id;
        int idAccount= Integer.parseInt(s);
        accounts.get(idAccount).setAmountOfMoney(accounts.get(idAccount).getAmountOfMoney()-payment);




    }


}