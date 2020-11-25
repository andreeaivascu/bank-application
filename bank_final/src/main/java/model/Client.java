package model;

import java.util.List;

public class Client {

    private Long id;
    private Long pnc;
    private String nameClient;
    private Long identityCardNumber;
    private String addressClient;
    private Long account;

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }


    public Long getPnc() {
        return pnc;
    }

    public String getNameClient() {
        return nameClient;
    }

    public Long getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getAddressClient() {
        return addressClient;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setPnc(Long pnc) {
        this.pnc = pnc;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public void setIdentityCardNumber(Long identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

}
