package fpt.capstone.vuondau.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class AccountDetailClassLevelKey implements Serializable {

    @Column(name = "account_detail_id")
    private Long accountDetailId;

    @Column(name = "class_level_id")
    private Long classLevelId;

    public Long getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Long accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public Long getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(Long classLevelId) {
        this.classLevelId = classLevelId;
    }
}
