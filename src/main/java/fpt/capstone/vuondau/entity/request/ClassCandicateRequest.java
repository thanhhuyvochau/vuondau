package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EGenderType;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class ClassCandicateRequest implements Serializable {
    private Long classId;
    private Long teacherId;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
