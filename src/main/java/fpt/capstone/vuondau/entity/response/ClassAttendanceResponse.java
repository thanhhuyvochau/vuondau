package fpt.capstone.vuondau.entity.response;


import fpt.capstone.vuondau.entity.StudentClassKey;
import fpt.capstone.vuondau.entity.TimeTable;
import fpt.capstone.vuondau.entity.common.EDayOfWeekCode;
import fpt.capstone.vuondau.entity.common.ESlotCode;
import fpt.capstone.vuondau.entity.dto.AttendanceDto;
import fpt.capstone.vuondau.entity.dto.TimeTableDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;

public class ClassAttendanceResponse {


    private Long accountId;
    private Long classId ;
    private List<AttendanceDto> attendance;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<AttendanceDto> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AttendanceDto> attendance) {
        this.attendance = attendance;
    }
}
