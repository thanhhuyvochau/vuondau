package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.EClassLevelCode;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.common.EClassType;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;

import java.math.BigDecimal;
import java.time.Instant;

public class ClassDto {

    private Long id ;
    private String name;
    private String code;

    private EClassStatus status ;

    private ClassTypeDto classType ;


    private Instant startDate ;

    private Instant endDate ;

//    private EClassLevelCode classLevel;

    private Long numberStudent ;

    private Long maxNumberStudent ;

    private CourseResponse course ;

    private BigDecimal unitPrice ;

    private BigDecimal finalPrice ;

    private AccountResponse teacher  ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(AccountResponse teacher) {
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


//    public EClassLevelCode getClassLevel() {
//        return classLevel;
//    }

    public ClassTypeDto getClassType() {
        return classType;
    }

    public void setClassType(ClassTypeDto classType) {
        this.classType = classType;
    }


//    public void setClassLevel(EClassLevelCode classLevel) {
//        this.classLevel = classLevel;
//    }

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
}
