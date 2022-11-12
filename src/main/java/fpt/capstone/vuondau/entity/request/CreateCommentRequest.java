package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.response.AccountResponse;

import java.util.ArrayList;
import java.util.List;


public class CreateCommentRequest {

    private String content;

    private Long questionId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
