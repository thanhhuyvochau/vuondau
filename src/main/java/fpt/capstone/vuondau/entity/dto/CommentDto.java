package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.VoteNumberReponse;
import fpt.capstone.vuondau.entity.response.AccountSimpleResponse;

import java.util.ArrayList;
import java.util.List;


public class CommentDto {


    private Long id;

    private String content;

    private VoteNumberReponse voteNumber;

    private List<CommentDto> subComments = new ArrayList<>();


    private CommentDto parentComment;


    private AccountSimpleResponse user;

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

    public VoteNumberReponse getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(VoteNumberReponse voteNumber) {
        this.voteNumber = voteNumber;
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

    public AccountSimpleResponse getUser() {
        return user;
    }

    public void setUser(AccountSimpleResponse user) {
        this.user = user;
    }
}
