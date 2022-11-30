package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EAccountRole;

import java.io.Serializable;
import java.time.Instant;

public class AccountEditRequest implements Serializable {

    private String name;
    private Instant birthDay ;
    private String phone ;

    private String mail ;

    private EAccountRole role ;

    private boolean isActive ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EAccountRole getRole() {
        return role;
    }

    public void setRole(EAccountRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
