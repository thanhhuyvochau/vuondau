package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import java.util.ArrayList;
import java.util.List;


public class CreateQuestionRequest {
    private String content;

    private SubjectResponse subject;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SubjectResponse getSubject() {
        return subject;
    }

    public void setSubject(SubjectResponse subject) {
        this.subject = subject;
    }
}
