package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EGradeType;

import java.util.ArrayList;
import java.util.List;

public class CourseRequest {

    private String name;

    private String code;

    private EGradeType gradeType;

    private Long subjectId;

    private List<Long> teacherIds = new ArrayList<>();

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

    public EGradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(EGradeType gradeType) {
        this.gradeType = gradeType;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<Long> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<Long> teacherIds) {
        this.teacherIds = teacherIds;
    }
}
