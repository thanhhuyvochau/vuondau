package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.ClassLevel;
import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


public class CreateClassRequest implements Serializable {


    private String name;
    private String code;
    private Instant startDate;
    private Instant endDate;
    private EClassLevel classLevel;

    private EClassType classType;
    private Long minNumberStudent;

    private Long maxNumberStudent;



    private BigDecimal eachStudentPayPrice ;


    public EClassType getClassType() {
        return classType;
    }

    public void setClassType(EClassType classType) {
        this.classType = classType;
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


    public EClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(EClassLevel classLevel) {
        this.classLevel = classLevel;
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


    public void setMaxNumberStudent(Long maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }

    public BigDecimal getEachStudentPayPrice() {
        return eachStudentPayPrice;
    }

    public void setEachStudentPayPrice(BigDecimal eachStudentPayPrice) {
        this.eachStudentPayPrice = eachStudentPayPrice;
    }
}
