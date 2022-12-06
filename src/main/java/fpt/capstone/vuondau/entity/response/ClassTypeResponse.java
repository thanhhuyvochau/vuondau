package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.EClassLevel;


public class ClassTypeResponse {
    private Long id;
    private String name;

    private EClassLevel code;


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

    public EClassLevel getCode() {
        return code;
    }

    public void setCode(EClassLevel code) {
        this.code = code;
    }

}
