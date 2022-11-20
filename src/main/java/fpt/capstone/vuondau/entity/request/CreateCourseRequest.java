package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EGradeType;

import java.util.ArrayList;
import java.util.List;

public class CreateCourseRequest {

    private Long subjectId ;

    private String name;

    private String code;


    private String title;

    private String description ;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
