package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.RequestType;
import fpt.capstone.vuondau.entity.common.ERequestStatus;

import javax.persistence.*;


public class VoteRequest {
    private Long questionId;
    private Long commentId;
    private Boolean isVote = false;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Boolean getVote() {
        return isVote;
    }

    public void setVote(Boolean vote) {
        isVote = vote;
    }
}
