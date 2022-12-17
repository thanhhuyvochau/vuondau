package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EGradeType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private StudentClassKey studentClassKeyId ;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_class_id")
//    private StudentClass studentClass;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_table_id")
    private TimeTable timeTable;

    @Column(name = "is_present")
    private Boolean isPresent ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }
}
