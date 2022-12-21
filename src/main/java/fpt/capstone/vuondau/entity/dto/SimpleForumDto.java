package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import java.util.ArrayList;
import java.util.List;


public class SimpleForumDto {

    private Long id;

    private String name;

    private String code;


    private EForumType type;

    private String subjectName;
    private String subjectCode;
    private String className;
    private String classCode;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EForumType getType() {
        return type;
    }

    public void setType(EForumType type) {
        this.type = type;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
