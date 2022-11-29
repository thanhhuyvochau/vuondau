package fpt.capstone.vuondau.entity;

import javax.persistence.*;

@Entity
@Table(name = "account_detail_subject")
public class AccountDetailClassLevel {

    @EmbeddedId
    private AccountDetailClassLevelKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accountDetailId")
    @JoinColumn(name = "account_detail_id")
    private AccountDetail accountDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classLevelId")
    @JoinColumn(name = "classlevel_id")
    private ClassLevel classLevel;

    public AccountDetailClassLevelKey getId() {
        return id;
    }

    public void setId(AccountDetailClassLevelKey id) {
        this.id = id;
    }

    public AccountDetail getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public ClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevel classLevel) {
        this.classLevel = classLevel;
    }
}
