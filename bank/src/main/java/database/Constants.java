package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {


        public static final String ACCOUNT = "repository_account";
        public static final String CLIENT = "repository_client";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT_ACCOUNT= "client_account";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, ACCOUNT, CLIENT,CLIENT_ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";


        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String ADD_CLIENT = "add_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "delete_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";



        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String VIEW_EMPLOYEE = "view_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String PROCESS_EMPLOYEE = "process_employee";

        public static final String[] RIGHTS = new String[]{ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT,  CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS, CREATE_EMPLOYEE, VIEW_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE, PROCESS_EMPLOYEE};
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_EMPLOYEE, VIEW_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE, PROCESS_EMPLOYEE));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS));



        return ROLES_RIGHTS;
    }

}
