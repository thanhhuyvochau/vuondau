package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EEvaluateType;


import javax.persistence.*;


@Entity
@Table(name = "feed_back")
public class FeedBack  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Account student;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Class clazz  ;

    private String content ;

    private EEvaluateType eEvaluateType ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
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
