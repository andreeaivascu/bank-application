package org.launchcode.models;


import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Entity
public class User extends AbstractEntity{

    @NotBlank
    @Size(min = 3, max = 50,message = "Size should be between 3 and 50 characters!")
    private String name;

    @NotBlank
    @Size(min = 10 ,message = "Size should be 10 digits!")
    private String telephone;

    @NotBlank
    @Size(min = 10, max = 50,message = "Size should be between 10 and 50 characters!")
    private String address;

    @NotBlank
    @Size( max = 13,message = "Size should be 13 digits!")
    private String CNP;

    @NotBlank
    @Email(message = "Invalid EMAIL! Try again.")
    private String email;


    @NotBlank
    @Size(min = 3, max = 70,message = "Size should be between 3 and 50 characters!")
    private String password;


    private String role;


    public User(String name, String telephone, String address, String CNP, String email,String password) {
        this.name = name;
        this.telephone=telephone;
        this.address=address;
        this.CNP=CNP;
        this.email=email;
        this.password=password;
        this.role="USER";

    }

    public User() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
