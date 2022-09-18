package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "student_class")
public class StudentClass {
    @EmbeddedId
    private StudentClassKey id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "class_id")
    @MapsId("classId")
    private Class aClass;

    public StudentClassKey getId() {
        return id;
    }

    public void setId(StudentClassKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
