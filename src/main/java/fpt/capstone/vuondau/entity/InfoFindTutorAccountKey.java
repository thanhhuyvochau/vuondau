package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class InfoFindTutorAccountKey implements Serializable {

    @Column(name = "info_find_tutorl_id")
    private Long infoFindTutorId;

    @Column(name = "teacher_id")
    private Long teacherId;

    public Long getInfoFindTutorId() {
        return infoFindTutorId;
    }

    public void setInfoFindTutorId(Long infoFindTutorId) {
        this.infoFindTutorId = infoFindTutorId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
