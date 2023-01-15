package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.BaseEntity;
import fpt.capstone.vuondau.entity.response.AccountSimpleResponse;

import javax.persistence.*;


public class MarkDto extends BaseEntity {


    private Long id;

    private AccountSimpleResponse student;

    private Float mark;

    private Float maxMark;

    private Float minMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountSimpleResponse getStudent() {
        return student;
    }

    public void setStudent(AccountSimpleResponse student) {
        this.student = student;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public Float getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Float maxMark) {
        this.maxMark = maxMark;
    }

    public Float getMinMark() {
        return minMark;
    }

    public void setMinMark(Float minMark) {
        this.minMark = minMark;
    }
}
