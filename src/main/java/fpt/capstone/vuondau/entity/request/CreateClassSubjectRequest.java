package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


public class CreateClassSubjectRequest implements Serializable {


    private Long subjectId;

    private Long courseId;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
