package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentClassKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "class_id")
    private Integer classId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}



