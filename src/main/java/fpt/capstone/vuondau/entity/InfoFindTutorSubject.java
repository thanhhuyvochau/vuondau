package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "info_find_tutor_subject")
public class InfoFindTutorSubject {

    @EmbeddedId
    private InformationFindTutorSubjectKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("infoFindTutorId")
    @JoinColumn(name = "info_find_tutorl_id")
    private InfoFindTutor infoFindTutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public InformationFindTutorSubjectKey getId() {
        return id;
    }

    public void setId(InformationFindTutorSubjectKey id) {
        this.id = id;
    }



    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
