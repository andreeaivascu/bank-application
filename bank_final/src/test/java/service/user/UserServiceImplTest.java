package service.user;

import database.DBConnectionFactory;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepositoryMock;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import java.sql.Connection;

public class UserServiceImplTest {



    private static UserService userService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        userService = new UserServiceImpl(
                userRepository,
                rightsRolesRepository
        );
    }

    @Test
    public void save() throws Exception {

        Assert.assertTrue(
                userService.save("anaemployee@yahoo.com","aceasta este3 o parola.").getResult()

        );

    }


    @Test
    public void update() throws Exception {

        Assert.assertTrue(
                userService.updateUser(1L,"aceasta este o parola 1 noua.").getResult()

        );

    }

    @Test
    public void findById() throws Exception {
        User user= userService.findById(1L);
        Assert.assertTrue(user!=null);

    }

    @Test
    public void delete() throws Exception {
        userService.save("employee2@yahoo.com","aceast3a este o parola.");
        Assert.assertTrue(
                userService.deleteUser(2L));

    }




}
