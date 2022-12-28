package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.VoteNumberReponse;
import fpt.capstone.vuondau.entity.response.AccountSimpleResponse;
import fpt.capstone.vuondau.entity.response.SubjectSimpleResponse;

public class QuestionSimpleDto {
    private Long id;

    private String content;

    private Boolean isClosed;

    private AccountSimpleResponse user;

    private SubjectSimpleResponse subject;

    private VoteNumberReponse voteNumber;

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

    public AccountSimpleResponse getUser() {
        return user;
    }

    public void setUser(AccountSimpleResponse user) {
        this.user = user;
    }

    public SubjectSimpleResponse getSubject() {
        return subject;
    }

    public void setSubject(SubjectSimpleResponse subject) {
        this.subject = subject;
    }

    public VoteNumberReponse getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(VoteNumberReponse voteNumber) {
        this.voteNumber = voteNumber;
    }
}
