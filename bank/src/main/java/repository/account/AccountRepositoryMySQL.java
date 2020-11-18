package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from repository_account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from repository_account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }


    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO repository_account values (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getIdentificationNumber());
            insertStatement.setString(2, account.getType());
            insertStatement.setDouble(3, account.getAmountOfMoney());
            insertStatement.setDate(4, new java.sql.Date(account.getDateOfCreation().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
           /* Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0 ";
            statement.executeUpdate(sql);*/

             PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from repository_account where id >= ?" );
            removeStatement.setLong(1,0 );
            removeStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try {
             PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from repository_account where id = ?" );
            removeStatement.setLong(1,id );
            removeStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update accounts involved in money transfer
    private boolean updateAccount(Double amountOfMoney, Long id)
    {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE repository_account set amountOfMoney = ? where id = ?");
            updateStatement.setDouble(1, amountOfMoney);
            updateStatement.setLong(2, id);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //transfer money between accounts
    @Override
    public boolean transferMoney(Long idSend, Long idReceive, Double sum) {

        try {
            Statement statement = connection.createStatement();

            String sqlSend = "Select * from repository_account where id=" + idSend;
            ResultSet rs = statement.executeQuery(sqlSend);
            Account accountSend=getAccountFromResultSet(rs);

            String sqlReceive = "Select * from repository_account where id=" + idReceive;
            ResultSet rsReceive = statement.executeQuery(sqlReceive);
            Account accountReceive=getAccountFromResultSet(rs);

            if(accountSend.getAmountOfMoney()>sum)
            {
                updateAccount(accountSend.getAmountOfMoney()-sum, idSend);
                updateAccount(accountReceive.getAmountOfMoney()+sum, idReceive);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean processBill(Long id, Double payment) {


        try {
            Statement statement = connection.createStatement();

            String sqlSend = "Select * from repository_account where id=" + id;
            ResultSet rs = statement.executeQuery(sqlSend);
            Account accountPayment=getAccountFromResultSet(rs);



            if(accountPayment.getAmountOfMoney()>payment)
            {
                updateAccount(accountPayment.getAmountOfMoney()-payment, id);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }




    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setIdentificationNumber(rs.getLong("identificationNumber"))
                .setType(rs.getString("type"))
                .setAmountOfMoney(rs.getDouble("amountOfMoney"))
                .setDateOfCreation(new Date(rs.getDate("dateOfCreation").getTime()))
                .build();
    }

}