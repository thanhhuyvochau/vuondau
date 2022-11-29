package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.response.AccountResponse;

public class StudentRequest {
    private AccountRequest account;


    private String name;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    public AccountRequest getAccount() {
        return account;
    }

    public void setAccount(AccountRequest account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
