package fpt.capstone.vuondau.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher_course")
public class TeacherCourse {

    @EmbeddedId
    private TeacherCourseKey id;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Account account;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;


    @Column(nullable = true, columnDefinition = "bit default 0")
    private Boolean isAllowed ;

    public TeacherCourseKey getId() {
        return id;
    }

    public void setId(TeacherCourseKey id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean allowed) {
        isAllowed = allowed;
    }
}
