package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.dto.RoleDto;
import fpt.capstone.vuondau.entity.response.GenderResponse;

import java.time.Instant;

public class CreateAccountRequest {

    private String email;

    private String password;
    private EAccountRole role;

    public EAccountRole getRole() {
        return role;
    }

    public void setRole(EAccountRole role) {
        this.role = role;
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
}
