package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher_course")
public class TeacherCourse {

    @EmbeddedId
    private TeacherCourseKey id = new TeacherCourseKey() ;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teachId")
    private Teacher teacher ;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course  ;




    public TeacherCourseKey getId() {
        return id;
    }

    public void setId(TeacherCourseKey id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


}
