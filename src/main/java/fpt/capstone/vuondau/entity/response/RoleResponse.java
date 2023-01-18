package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.EAccountRole;

import java.util.List;

public class RoleResponse {
    // NOTE về sau trả thêm cả user trong role.
    private Long id;
    private String name;
    private EAccountRole code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
