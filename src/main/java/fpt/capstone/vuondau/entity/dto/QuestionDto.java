package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionDto {


    private Long id;

    private String content;

    private Boolean isClosed;


    private AccountResponse student;


    private SubjectResponse subject;

    private Integer upvoteNumber;

    private Integer downVoteNumber;

    private List<CommentDto> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public AccountResponse getStudent() {
        return student;
    }

    public void setStudent(AccountResponse student) {
        this.student = student;
    }

    public SubjectResponse getSubject() {
        return subject;
    }

    public void setSubject(SubjectResponse subject) {
        this.subject = subject;
    }

    public Integer getUpvoteNumber() {
        return upvoteNumber;
    }

    public void setUpvoteNumber(Integer upvoteNumber) {
        this.upvoteNumber = upvoteNumber;
    }

    public Integer getDownVoteNumber() {
        return downVoteNumber;
    }

    public void setDownVoteNumber(Integer downVoteNumber) {
        this.downVoteNumber = downVoteNumber;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
