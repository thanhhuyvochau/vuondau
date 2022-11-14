package fpt.capstone.vuondau.entity.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fpt.capstone.vuondau.entity.common.EAccountRole;

import java.io.Serializable;

public class AccountExistedTeacherRequest implements Serializable {

    private String username;
    private String password;

    @JsonIgnore
    private boolean isActive = false ;

    private String firstName ;

    private String lastName ;

    private String  phone ;

    @JsonIgnore
    private String roleAccount ;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String  getPhone() {
        return phone;
    }

    public void setPhone(String  phone) {
        this.phone = phone;
    }


    public String getRoleAccount() {
        return roleAccount;
    }

    public void setRoleAccount(String roleAccount) {
        this.roleAccount = roleAccount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
