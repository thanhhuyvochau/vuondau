package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseDtoResponse;
import fpt.capstone.vuondau.entity.TeacherCourseKey;
import fpt.capstone.vuondau.entity.TimeTable;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ClassDetailDto {

    private Long id;
    private String name;
    private String code;

    private EClassStatus status;

    private Instant startDate;

    private Instant endDate;

    private ClassTypeDto classType;


    private BigDecimal unitPrice;


    private BigDecimal finalPrice;

    private Long numberStudent;

    private Long maxNumberStudent;

    private boolean isActive;

//    private CourseResponse course ;

    private CourseDetailResponse course;

    private List<MoodleRecourseDtoResponse> resources;

    private AccountResponse teacher;

    private List<AccountResponse> students;


    private List<TimeTableDto> timeTable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
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


    public ClassTypeDto getClassType() {
        return classType;
    }

    public void setClassType(ClassTypeDto classType) {
        this.classType = classType;
    }

    public Long getNumberStudent() {
        return numberStudent;
    }

    public void setNumberStudent(Long numberStudent) {
        this.numberStudent = numberStudent;
    }

    public EClassStatus getStatus() {
        return status;
    }

    public void setStatus(EClassStatus status) {
        this.status = status;
    }

    public Long getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public void setMaxNumberStudent(Long maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<MoodleRecourseDtoResponse> getResources() {
        return resources;
    }

    public void setResources(List<MoodleRecourseDtoResponse> resources) {
        this.resources = resources;
    }

    public CourseDetailResponse getCourse() {
        return course;
    }

    public void setCourse(CourseDetailResponse course) {
        this.course = course;
    }

    public AccountResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(AccountResponse teacher) {
        this.teacher = teacher;
    }

    public List<AccountResponse> getStudents() {
        return students;
    }

    public void setStudents(List<AccountResponse> students) {
        this.students = students;
    }

    public List<TimeTableDto> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<TimeTableDto> timeTable) {
        this.timeTable = timeTable;
    }
}
