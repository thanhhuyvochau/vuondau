package fpt.capstone.vuondau.entity.request;

import java.io.Serializable;
import java.time.Instant;


public class CreateClassRequest implements Serializable {


    private String name;
    private String code;
    private Instant startDate;
    private Instant endDate;
    private String level;

    private Long minNumberStudent;

    private Long maxNumberStudent;

    private Long subjectId;

    private Long courseId;

//    private CreateCourseRequest courseRequest ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getMinNumberStudent() {
        return minNumberStudent;
    }

    public void setMinNumberStudent(Long minNumberStudent) {
        this.minNumberStudent = minNumberStudent;
    }

    public Long getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setMaxNumberStudent(Long maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    //    public CreateCourseRequest getCourseRequest() {
//        return courseRequest;
//    }
//
//    public void setCourseRequest(CreateCourseRequest courseRequest) {
//        this.courseRequest = courseRequest;
//    }
}
