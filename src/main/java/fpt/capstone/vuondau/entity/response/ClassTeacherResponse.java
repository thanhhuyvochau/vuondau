package fpt.capstone.vuondau.entity.response;


import fpt.capstone.vuondau.entity.common.EDayOfWeekCode;
import fpt.capstone.vuondau.entity.common.ESlotCode;

import java.time.Instant;

public class ClassTeacherResponse {

    private AccountResponse teacher;

    public AccountResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(AccountResponse teacher) {
        this.teacher = teacher;
    }
}
