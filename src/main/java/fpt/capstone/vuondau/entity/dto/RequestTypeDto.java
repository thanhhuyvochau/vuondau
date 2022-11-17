package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EResourceType;

public class RequestTypeDto {

    private Long id;
    private String name;
    private EResourceType code;

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

    public EResourceType getCode() {
        return code;
    }

    public void setCode(EResourceType code) {
        this.code = code;
    }
}
