package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.repository.QuestionRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IQuestionService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements IQuestionService {
    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final SecurityUtil securityUtil;

    public QuestionServiceImpl(QuestionRepository questionRepository, SubjectRepository subjectRepository, SecurityUtil securityUtil) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question not found by id:" + id));
        return ConvertUtil.doConvertEntityToResponse(question);
    }

    @Override
    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(ConvertUtil::doConvertEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getQuestionsBySubject(Long subjectId) {
        List<Question> questions = questionRepository.findAllBySubject_Id(subjectId);
        return questions.stream().map(ConvertUtil::doConvertEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest) {
        Question question = ObjectUtil.copyProperties(createQuestionRequest, new Question(), Question.class, true);
        Account student = securityUtil.getCurrentUser();
        Subject subject = subjectRepository.findById(createQuestionRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Subject not found with id:" + createQuestionRequest.getSubjectId()));
        question.setSubject(subject);
        question.setStudent(student);
        questionRepository.save(question);
        return ConvertUtil.doConvertEntityToResponse(question);
    }

    @Override
    public QuestionDto updateQuestion(Long questionId, CreateQuestionRequest createQuestionRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Question not found with id:" + questionId));
        Question newQuestion = ObjectUtil.copyProperties(createQuestionRequest, question, Question.class, true);
        Account student = securityUtil.getCurrentUser();
        Subject subject = subjectRepository.findById(createQuestionRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Subject not found with id:" + createQuestionRequest.getSubjectId()));
        newQuestion.setSubject(subject);
        newQuestion.setStudent(student);
        questionRepository.save(newQuestion);
        return ConvertUtil.doConvertEntityToResponse(newQuestion);
    }

    @Override
    public Boolean closeQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question not found by id:" + id));
        if (question.getClosed()) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question has already closed!");
        } else {
            question.setClosed(true);
        }
        return true;
    }
}
