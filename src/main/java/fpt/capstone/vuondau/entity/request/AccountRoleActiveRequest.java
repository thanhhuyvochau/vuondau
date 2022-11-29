package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EAccountRole;

import java.io.Serializable;


public class AccountRoleActiveRequest implements Serializable {


    private EAccountRole role ;

    private boolean isActive ;


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
