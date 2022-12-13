package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.ECandicateStatus;

public class CandicateResponse {
    private AccountResponse teacher;
    private ECandicateStatus status;

    public AccountResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(AccountResponse teacher) {
        this.teacher = teacher;
    }

    public ECandicateStatus getStatus() {
        return status;
    }

    public void setStatus(ECandicateStatus status) {
        this.status = status;
    }
}
