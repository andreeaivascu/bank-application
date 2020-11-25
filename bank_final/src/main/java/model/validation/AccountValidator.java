package model.validation;

import model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {


    private static final String DATE_VALIDATOR = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private static final String IDENTIFICATION_NUMBER_VALIDATOR= "^[0-9]\\d{15}$";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Account account;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate() {
       // validateDate(""+account.getDateOfCreation());
        validateIdentificationNumber(""+account.getIdentificationNumber());
        validateType(account.getType());
        return errors.isEmpty();
    }

    private void validateDate(String date) {
        if (!Pattern.compile(DATE_VALIDATOR).matcher(date).matches()) {
            errors.add("Invalid dateOfCreation!");
        }
    }

    private void validateIdentificationNumber(String identificationNumber) {
        if (!Pattern.compile(IDENTIFICATION_NUMBER_VALIDATOR).matcher(identificationNumber).matches()) {
            errors.add("Invalid identificationNumber!");
        }
    }

    private void validateType(String type) {
        if (type.isEmpty()) {
            errors.add("complete all the fields (*)!!");
        }
    }
}
