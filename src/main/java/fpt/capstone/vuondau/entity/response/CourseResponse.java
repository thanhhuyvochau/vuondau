package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.CourseDetailDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.dto.TeacherCourseDto;
import fpt.capstone.vuondau.entity.common.EGradeType;

import java.util.List;

public class CourseResponse {
    private Long id;
    private String name;

    private String code;

    private String description ;

    private EGradeType grade;



    private ClassDto clazz ;

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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClassDto getClazz() {
        return clazz;
    }

    public void setClazz(ClassDto clazz) {
        this.clazz = clazz;
    }
}
