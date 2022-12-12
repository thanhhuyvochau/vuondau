package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "class_teacher_candicate")
public class ClassTeacherCandicate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "class_id")
    @ManyToOne
    private Class clazz;
    @JoinColumn(name = "teacher_id")
    @ManyToOne
    private Account teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account teacher) {
        this.teacher = teacher;
    }
}
