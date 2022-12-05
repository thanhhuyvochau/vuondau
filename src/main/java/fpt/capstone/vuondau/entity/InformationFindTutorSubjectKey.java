package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class InformationFindTutorSubjectKey implements Serializable {

    @Column(name = "info_find_tutorl_id")
    private Long infoFindTutorId;

    @Column(name = "subject_id")
    private Long subjectId;

    public Long getInfoFindTutorId() {
        return infoFindTutorId;
    }

    public void setInfoFindTutorId(Long infoFindTutorId) {
        this.infoFindTutorId = infoFindTutorId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
