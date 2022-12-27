package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.common.EClassType;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountSimpleResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;

import java.math.BigDecimal;
import java.time.Instant;

public class ClassDto {

    private Long id ;
    private String name;
    private String code;

    private EClassStatus status ;

    private EClassType classType ;

    private EClassLevel classLevel ;


    private Instant startDate ;

    private Instant endDate ;


    private Long numberStudent ;

    private Long maxNumberStudent ;
    private Long minNumberStudent ;

    private CourseResponse course ;

    private BigDecimal eachStudentPayPrice ;

    private BigDecimal finalPrice ;

    private AccountSimpleResponse teacher  ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountSimpleResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(AccountSimpleResponse teacher) {
        this.teacher = teacher;
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

    public BigDecimal getEachStudentPayPrice() {
        return eachStudentPayPrice;
    }

    public void setEachStudentPayPrice(BigDecimal eachStudentPayPrice) {
        this.eachStudentPayPrice = eachStudentPayPrice;
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


    public EClassType getClassType() {
        return classType;
    }

    public void setClassType(EClassType classType) {
        this.classType = classType;
    }

    public EClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(EClassLevel classLevel) {
        this.classLevel = classLevel;
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

    public CourseResponse getCourse() {
        return course;
    }

    public void setCourse(CourseResponse course) {
        this.course = course;
    }

    public Long getMinNumberStudent() {
        return minNumberStudent;
    }

    public void setMinNumberStudent(Long minNumberStudent) {
        this.minNumberStudent = minNumberStudent;
    }
}
