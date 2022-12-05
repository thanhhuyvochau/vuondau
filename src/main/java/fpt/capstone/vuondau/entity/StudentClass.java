package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "student_class")
public class StudentClass {
    @EmbeddedId
    private StudentClassKey id = new StudentClassKey();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Account account;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private Class aClass;


    @Column(name = "enroll_date")
    private Instant enrollDate;

    @Column(name = "is_enrolled")
    private boolean is_enrolled;

    public StudentClassKey getId() {
        return id;
    }

    public void setId(StudentClassKey id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setAClass(Class aClass) {
        this.aClass = aClass;
    }

    public Instant getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Instant enrollDate) {
        this.enrollDate = enrollDate;
    }

    public boolean isIs_enrolled() {
        return is_enrolled;
    }

    public void setIs_enrolled(boolean is_enrolled) {
        this.is_enrolled = is_enrolled;
    }
}