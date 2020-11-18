package model;

import java.util.Date;

public class Account {
    private Long id;
    private Long identificationNumber;
    private String type;
    private Double amountOfMoney;
    private Date dateOfCreation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentificationNumber() {
        return identificationNumber;
    }

    public String getType() {
        return type;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setIdentificationNumber(Long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
