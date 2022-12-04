package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EClassType;
import fpt.capstone.vuondau.entity.common.ESlotCode;

public class ClassTypeDto {

    private Long id;
    private EClassType code;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EClassType getCode() {
        return code;
    }

    public void setCode(EClassType code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
