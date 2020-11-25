package model.validation;

import model.Client;
import model.User;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String PNC_VALIDATOR = "^[0-9]\\d{12}$";

    private static final String IDENTITY_CARD_NUMBER_VALIDATOR = "^[0-9]\\d{15}$";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }


    public boolean validate() {
        validatePNC(""+client.getPnc());
        validateIdentityCardNumber(""+client.getIdentityCardNumber());
        validateNameClient(client.getNameClient());
        validateAddressClient(client.getAddressClient());
        return errors.isEmpty();
    }

    private void validatePNC(String pnc) {
        if (!Pattern.compile(PNC_VALIDATOR).matcher(pnc).matches()) {
            errors.add("Invalid pnc!");
        }
    }

    private void validateIdentityCardNumber(String identityCardNumber) {
        if (!Pattern.compile(IDENTITY_CARD_NUMBER_VALIDATOR).matcher(identityCardNumber).matches()) {
            errors.add("Invalid identityCardNumber!");
        }
    }

    private void validateNameClient(String nameClient) {
        if (nameClient.isEmpty()) {
            errors.add("Complete all fields!!");
        }
    }

    private void validateAddressClient(String addressClient) {
        if (addressClient.isEmpty()) {
            errors.add("Complete all fields!!");
        }
    }
}
