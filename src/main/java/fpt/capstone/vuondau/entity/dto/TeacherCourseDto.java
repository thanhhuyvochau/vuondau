package fpt.capstone.vuondau.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class TeacherCourseDto {


    private Long topicId;


    private Long teacherId;

    private String teacherName ;


    @Schema(nullable = true)
    private Boolean isAllowed;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
