package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.entity.request.VoteRequest;

import java.util.List;

public interface IQuestionService {
    QuestionDto getQuestion(Long id);
    QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest);

    QuestionDto updateQuestion(Long questionId, CreateQuestionRequest createQuestionRequest);

    Boolean closeQuestion(Long questionId);

    Boolean openQuestion(Long id);

    Boolean voteQuestion(VoteRequest request);
}
