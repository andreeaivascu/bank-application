package repository.client;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.security.RightsRolesRepository;
import service.account.AccountService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;


    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;

    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from repository_client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO repository_client values (null, ?, ?, ?, ?)" , Statement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(1, client.getPnc());
            insertStatement.setString(2, client.getNameClient());
            insertStatement.setLong(3, client.getIdentityCardNumber());
            insertStatement.setString(4, client.getAddressClient());
            //insertStatement.setAccount(5,)
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

            client.setAccount(client.getAccount());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from repository_client where id >= 0 ";
            statement.executeUpdate(sql);

            /*  PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from account where id = ?" );
            removeStatement.setLong(1,id );
            removeStatement.executeUpdate();*/


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try {
            PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from repository_client where id = ?" );
            removeStatement.setLong(1,id );
            removeStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setPnc(rs.getLong("pnc"))
                .setNameClient(rs.getString("nameClient"))
                .setIdentityCardNumber(rs.getLong("identityCardNumber"))
                .setAddressClient(rs.getString("addressClient"))
                .setAccount(rs.getObject("account", Account.class))

                .build();
    }




}
