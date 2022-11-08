package fpt.capstone.vuondau.entity;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;



@Embeddable
public class FeedBackKey  implements Serializable {

    @Column(name = "course_id")
    private Long courseId ;

    @Column(name = "student_id")
    private Long studentId;


}
