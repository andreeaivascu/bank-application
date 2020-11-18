package repository.user;

import model.Account;
import model.User;
import model.builder.AccountBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;


public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    //findAll
    @Override
    public List<User> findAll() {
        List<User>  users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                users.add(getUsersFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
     //   return null;



    }


    //getUsersFromResultSet
    private User getUsersFromResultSet(ResultSet rs) throws SQLException {

        User user=new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                //.setUsername(rs.getString("username"))
                //.setPassword(rs.getString("password"))
        return user;

    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

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
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //remove an user
    @Override
    public void remove(Long id) {
        try {
            PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from user where id = ?" );
            removeStatement.setLong(1,id );
            removeStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //update an information about user (username/password)
    @Override
    public void update(Long id, String columnToUpdate, String valueToUpdate) {
        try {
            PreparedStatement updateStatement= connection
                    .prepareStatement("UPDATE user set password = ? where id = ?" );
            //updateStatement.setString(1,columnToUpdate );
            updateStatement.setString(1,valueToUpdate );
            updateStatement.setLong(2,id );
          //  System.out.println(updateStatement);

            updateStatement.executeUpdate();
           //System.out.println(updateStatement);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
