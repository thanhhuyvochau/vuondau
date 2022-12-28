package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.dto.QuestionSimpleDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.entity.request.VoteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IQuestionService {
    QuestionDto getQuestion(Long id);
    QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest);

    QuestionDto updateQuestion(Long questionId, CreateQuestionRequest createQuestionRequest);

    Boolean closeQuestion(Long questionId);

    Boolean openQuestion(Long id);

    Boolean voteQuestion(VoteRequest request);

    ApiPage<QuestionSimpleDto> searchQuestion(Long forumId, String q, Pageable pageable);
}
