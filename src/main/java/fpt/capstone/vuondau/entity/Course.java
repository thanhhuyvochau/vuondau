package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EGradeType;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @JoinColumn(name="grade", nullable = false)
    private EGradeType grade;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="subject_id")
    private Subject subject;


    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<TeacherCourse> teacherCourses;


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

    public EGradeType getGrade() {
        return grade;
    }

    public void setGrade(EGradeType grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public List<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(List<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

}
