package fpt.capstone.vuondau.entity.Dto;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.persistence.Column;

public class TeacherCourseDto {


        private Long topicId;


        private Long teacherId;


        @Schema(nullable = true)
        private Boolean isAllowed ;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean allowed) {
        isAllowed = allowed;
    }
}
