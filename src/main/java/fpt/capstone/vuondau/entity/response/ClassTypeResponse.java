package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.request.EClassType;

import javax.persistence.*;
import java.util.List;


public class ClassTypeResponse {
    private Long id;
    private String name;

    private EClassType code;


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

    public EClassType getCode() {
        return code;
    }

    public void setCode(EClassType code) {
        this.code = code;
    }

}
