package repository.user;

import model.Account;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
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

       /* try{  PreparedStatement statement=connection
                .prepareStatement( "Select * from user");
            ResultSet rs=statement.executeQuery();
            while (rs.next()) {
                users.add(getUsersFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try{  PreparedStatement statement=connection
                .prepareStatement( "Select * from user");
            ResultSet rs=statement.executeQuery();
            while (rs.next()) {
                users.add(getUsersFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return users;

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
            PreparedStatement removeStatement= connection
                    .prepareStatement("DELETE from user where id >=0" );
            removeStatement.executeUpdate();


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
    public boolean update(Long id, String valueToUpdate) {
        try {
            PreparedStatement updateStatement= connection
                    .prepareStatement("UPDATE user set password = ? where id = ?" );
            //updateStatement.setString(1,columnToUpdate );
            updateStatement.setString(1,valueToUpdate );
            updateStatement.setLong(2,id );
          //  System.out.println(updateStatement);

            updateStatement.executeUpdate();
           //System.out.println(updateStatement);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
      /*  try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getUsersFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException | EntityNotFoundException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }*/

        try{  PreparedStatement statement=connection
                .prepareStatement( "Select * from user where id=" + id);
            ResultSet rs=statement.executeQuery();
            if (rs.next()) {
                return getUsersFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException | EntityNotFoundException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    //getUsersFromResultSet
    private User getUsersFromResultSet(ResultSet rs) throws SQLException {

        User user=new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;

    }


}
