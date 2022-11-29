package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class AccountDetailSubjectKey implements Serializable {

    @Column(name = "account_detail_id")
    private Long accountDetailId;

    @Column(name = "subject_id")
    private Long subjectId;

    public Long getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Long accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
