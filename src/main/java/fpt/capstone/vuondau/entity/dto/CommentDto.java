package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.response.AccountResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class CommentDto {


    private Long id;

    private String content;

    private Integer upvoteNumber;

    private Integer downVoteNumber;

    private List<CommentDto> subComments = new ArrayList<>();


    private CommentDto parentComment;


    private AccountResponse student;


    private Question question;

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

    public List<CommentDto> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<CommentDto> subComments) {
        this.subComments = subComments;
    }

    public CommentDto getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentDto parentComment) {
        this.parentComment = parentComment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
