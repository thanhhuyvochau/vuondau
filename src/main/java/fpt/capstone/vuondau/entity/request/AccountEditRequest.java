package fpt.capstone.vuondau.entity.request;


import java.io.Serializable;
import java.time.Instant;

public class AccountEditRequest implements Serializable {

    private String name;
    private Instant birthDay ;
    private String phone ;

    private String mail ;



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


}
