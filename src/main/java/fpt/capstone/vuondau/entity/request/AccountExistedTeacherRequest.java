package fpt.capstone.vuondau.entity.request;


import java.io.Serializable;

public class AccountExistedTeacherRequest implements Serializable {

    private String username;
    private String password;
    private boolean isActive = false ;
    private Long teachId ;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getTeachId() {
        return teachId;
    }

    public void setTeachId(Long teachId) {
        this.teachId = teachId;
    }
}
