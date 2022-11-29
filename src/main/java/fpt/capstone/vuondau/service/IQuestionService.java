package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;

import java.util.List;

public interface IQuestionService {
    QuestionDto getQuestion(Long id);

    List<QuestionDto> getQuestions();

    List<QuestionDto> getQuestionsBySubject(Long subjectId);

    QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest);

    QuestionDto updateQuestion(Long questionId, CreateQuestionRequest createQuestionRequest);

    Boolean closeQuestion(Long questionId);
}
