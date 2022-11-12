package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EQuestionType;
import fpt.capstone.vuondau.entity.dto.AnswerDto;

import java.io.Serializable;
import java.util.List;

public class StudentSurveyRequest implements Serializable {

    private Long questionId ;

    private Long answerId ;

    private String essayAnswer ;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getEssayAnswer() {
        return essayAnswer;
    }

    public void setEssayAnswer(String essayAnswer) {
        this.essayAnswer = essayAnswer;
    }
}
