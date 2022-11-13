package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.repository.QuestionRepository;
import fpt.capstone.vuondau.service.IQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionDto getQuestion(Long id) {
        return null;
    }

    @Override
    public List<QuestionDto> getQuestions() {
        return null;
    }

    @Override
    public List<QuestionDto> getQuestionsBySubject(Long subjectId) {
        return null;
    }

    @Override
    public QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest) {
        return null;
    }

    @Override
    public QuestionDto updateQuestion(Long questionId, CreateQuestionRequest createQuestionRequest) {
        return null;
    }

    @Override
    public Boolean closeQuestion(Long questionId) {
        return null;
    }
}
