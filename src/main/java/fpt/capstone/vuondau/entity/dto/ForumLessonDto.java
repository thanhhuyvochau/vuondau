package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.Forum;

import javax.persistence.*;
import java.util.List;


public class ForumLessonDto {

    private Long id;

    private String lessonName;

    private List<QuestionDto> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
