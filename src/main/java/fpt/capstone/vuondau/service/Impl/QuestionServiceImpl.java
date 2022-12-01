package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.QuestionDto;
import fpt.capstone.vuondau.entity.request.CreateQuestionRequest;
import fpt.capstone.vuondau.repository.ForumLessonRepository;
import fpt.capstone.vuondau.repository.ForumRepository;
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
    private final ForumRepository forumRepository;
    private final ForumLessonRepository forumLessonRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, SubjectRepository subjectRepository, SecurityUtil securityUtil, ForumRepository forumRepository, ForumLessonRepository forumLessonRepository) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.securityUtil = securityUtil;
        this.forumRepository = forumRepository;
        this.forumLessonRepository = forumLessonRepository;
    }

    @Override
    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question not found by id:" + id));
        return ConvertUtil.doConvertEntityToResponse(question);
    }

    @Override
    public QuestionDto createQuestion(CreateQuestionRequest createQuestionRequest) {
        Question question = ObjectUtil.copyProperties(createQuestionRequest, new Question(), Question.class, true);
        Account student = securityUtil.getCurrentUser();

        Forum forum = forumRepository.findById(createQuestionRequest.getForumId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Subject not found with id:" + createQuestionRequest.getForumId()));

        ForumLesson forumLesson = forumLessonRepository.findById(createQuestionRequest
                .getForumLessonId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                .withMessage("Lesson not found with id:" + createQuestionRequest.getForumLessonId()));

        question.setForumLesson(forumLesson);
        question.setForum(forum);
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
        Forum forum = forumRepository.findById(createQuestionRequest.getForumId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Forum not found with id:" + createQuestionRequest.getForumId()));
        newQuestion.setForum(forum);
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

    @Override
    public Boolean openQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question not found by id:" + id));
        if (!question.getClosed()) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Question has already opened!");
        } else {
            question.setClosed(false);
        }
        return true;
    }

    private Boolean isEnrolledToSubject(Account account, Subject subject) {
        List<Class> enrolledClass = account.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());
        Class classMatchSubject = enrolledClass.stream()
                .filter(aClass -> aClass.getCourse().getSubject() != null)
                .filter(aClass -> aClass.getCourse().getSubject().getId().equals(subject.getId()))
                .findFirst().orElseThrow(() -> ApiException.create(HttpStatus.CONFLICT).withMessage("Student not enrolled to this subject!!"));
        return true;
    }
}
