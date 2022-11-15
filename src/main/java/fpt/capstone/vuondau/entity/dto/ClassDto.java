package fpt.capstone.vuondau.entity.dto;


import java.time.Instant;

public class ClassDto {


    private String className;
    private String classCode;

    private long teacherId ;

    private long courseId ;

    private Instant startDate ;

    private Instant endDate ;

    private String level;

    private Long numberStudent ;




    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public Long getNumberStudent() {
        return numberStudent;
    }

    public void setNumberStudent(Long numberStudent) {
        this.numberStudent = numberStudent;
    }
}
