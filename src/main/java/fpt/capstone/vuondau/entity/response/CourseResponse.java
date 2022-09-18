package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.TeacherCourse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseResponse {
    private Long id;
    private String name;

    private String code;

    private GradeResponse grade;

    private SubjectResponse subject;

    private List<TeacherCourse> teacherCourses = new ArrayList<>() ;

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

    public GradeResponse getGrade() {
        return grade;
    }

    public void setGrade(GradeResponse grade) {
        this.grade = grade;
    }

    public SubjectResponse getSubject() {
        return subject;
    }

    public void setSubject(SubjectResponse subject) {
        this.subject = subject;
    }

    public List<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(List<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }
}
