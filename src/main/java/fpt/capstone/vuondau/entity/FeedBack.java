package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EEvaluateType;


import javax.persistence.*;


@Entity
@Table(name = "feed_back")
public class FeedBack {

    @EmbeddedId
    private PostResourceKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Account student;


    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToOne
    @JoinColumn(name="class_id")
    private Class clazz  ;

    private String content ;

    private EEvaluateType eEvaluateType ;

    public PostResourceKey getId() {
        return id;
    }

    public void setId(PostResourceKey id) {
        this.id = id;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EEvaluateType geteEvaluateType() {
        return eEvaluateType;
    }

    public void seteEvaluateType(EEvaluateType eEvaluateType) {
        this.eEvaluateType = eEvaluateType;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
