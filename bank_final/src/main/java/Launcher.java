import controller.EmployeeController;
import controller.LoginController;
import view.EmployeeView;
import view.LoginView;


public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance(false);
        new LoginController(new LoginView(), componentFactory.getAuthenticationService(), componentFactory.getRightsRolesRepository(), componentFactory.getUserRepository(), componentFactory.getAccountService(), componentFactory.getClientService(), componentFactory.getUserService(), componentFactory.getReportService());


    }

}
