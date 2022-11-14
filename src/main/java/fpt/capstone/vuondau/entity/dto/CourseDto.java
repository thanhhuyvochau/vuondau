package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EGradeType;

public class CourseDto {


    private String name;
    private String code;
    private EGradeType grade;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EGradeType getGrade() {
        return grade;
    }

    public void setGrade(EGradeType grade) {
        this.grade = grade;
    }
}
