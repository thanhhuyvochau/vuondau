package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EAccountRole;

public class RoleDto {


    private String name;


    private EAccountRole code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EAccountRole getCode() {
        return code;
    }

    public void setCode(EAccountRole code) {
        this.code = code;
    }
}
