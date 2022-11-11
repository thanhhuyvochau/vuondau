package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.Dto.SubjectDto;
import fpt.capstone.vuondau.entity.Dto.TeacherCourseDto;
import fpt.capstone.vuondau.entity.TeacherCourse;
import fpt.capstone.vuondau.entity.common.EGradeType;

import java.util.ArrayList;
import java.util.List;

public class CourseResponse {
    private Long id;
    private String name;

    private String code;

    private EGradeType grade;

    private SubjectDto subject;

    private List<TeacherCourseDto> teacherCourse ;



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


    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<TeacherCourseDto> getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(List<TeacherCourseDto> teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    public EGradeType getGrade() {
        return grade;
    }

    public void setGrade(EGradeType grade) {
        this.grade = grade;
    }
}
