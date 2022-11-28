package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.CourseDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ClassCourseResponse {


    private Long studentId ;
    private ClassDto clazz;
    private CourseDto course ;
    private SubjectDto subject;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public ClassDto getClazz() {
        return clazz;
    }

    public void setClazz(ClassDto clazz) {
        this.clazz = clazz;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }
}
