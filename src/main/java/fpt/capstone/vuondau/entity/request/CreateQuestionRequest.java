package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import java.util.ArrayList;
import java.util.List;


public class CreateQuestionRequest {
    private String content;

    private Long subjectId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
