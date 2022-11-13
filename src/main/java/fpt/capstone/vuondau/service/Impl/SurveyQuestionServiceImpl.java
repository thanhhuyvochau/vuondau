package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Answer;
import fpt.capstone.vuondau.entity.StudentAnswer;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EQuestionType;
import fpt.capstone.vuondau.entity.SurveyQuestion;
import fpt.capstone.vuondau.entity.dto.AnswerDto;
import fpt.capstone.vuondau.entity.request.StudentSurveyRequest;
import fpt.capstone.vuondau.entity.request.SurveyQuestionRequest;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.AnswerRepository;
import fpt.capstone.vuondau.repository.StudentAnswerRepository;
import fpt.capstone.vuondau.repository.SurveyQuestionRepository;
import fpt.capstone.vuondau.service.ISurveyQuestionService;
import fpt.capstone.vuondau.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SurveyQuestionServiceImpl implements ISurveyQuestionService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;
    private final SurveyQuestionRepository surveyQuestionRepository;

    private final AnswerRepository answerRepository;

    private final StudentAnswerRepository studentAnswerRepository;


    public SurveyQuestionServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, SurveyQuestionRepository surveyQuestionRepository, AnswerRepository answerRepository, StudentAnswerRepository studentAnswerRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.answerRepository = answerRepository;
        this.studentAnswerRepository = studentAnswerRepository;
    }

    @Override
    public Boolean adminCreateSurveyQuestion(List<SurveyQuestionRequest> surveyQuestionRequest) {
        List<SurveyQuestion> surveyQuestionRequestList = new ArrayList<>();
        for (SurveyQuestionRequest question : surveyQuestionRequest) {
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            surveyQuestion.setQuestion(question.getQuestion());
            surveyQuestion.setVisible(question.getVisible());
            List<Answer> answerList = new ArrayList<>();
            if (question.getQuestionType().equals(EQuestionType.MULTIPLE_CHOICE)) {

                for (AnswerDto answerMultipleChoice : question.getAnswersMultipleChoice()) {
                    Answer answer = new Answer();
                    answer.setAnswer(answerMultipleChoice.getAnswer());
                    answer.setSurveyQuestion(surveyQuestion);
                    answer.setVisible(answerMultipleChoice.isVisible());
                    answerList.add(answer);
                }
                surveyQuestion.setQuestionType(EQuestionType.MULTIPLE_CHOICE);
            } else {
                surveyQuestion.setQuestionType(EQuestionType.ESSAY);
            }
            surveyQuestion.setAnswers(answerList);
            surveyQuestion.setQuestionType(question.getQuestionType());

            surveyQuestionRequestList.add(surveyQuestion);
        }
        surveyQuestionRepository.saveAll(surveyQuestionRequestList);

        return true;
    }

    @Override
    public Boolean studentSubmitSurvey(Long studentId, List<StudentSurveyRequest> studentSurveyRequests) {
        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + studentId));

        List<SurveyQuestion> allSurveyQuestion = surveyQuestionRepository.findAll();

        List<StudentAnswer> studentAnswerList = new ArrayList<>();
        for (StudentSurveyRequest studentSurveyRequest : studentSurveyRequests) {
            SurveyQuestion survey = allSurveyQuestion.stream().filter(eachSurveyQuestion -> eachSurveyQuestion.getId().equals(studentSurveyRequest.getQuestionId())).findFirst()
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                            .withMessage(messageUtil.getLocalMessage("Survey Question khong tim thay") + studentSurveyRequest.getQuestionId()));

            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setStudent(account);
            studentAnswer.setSurveyQuestion(survey);
            if (survey.getQuestionType().equals(EQuestionType.ESSAY)) {
                studentAnswer.setOpenAnswer(studentSurveyRequest.getEssayAnswer());
            } else {
                Answer answer = answerRepository.findByIdAndSurveyQuestion(studentSurveyRequest.getAnswerId(), survey)
                        .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay answer") + studentId));
                studentAnswer.setAnswer(answer);
            }
            studentAnswerList.add(studentAnswer);

        }
        if (studentAnswerList.size() < allSurveyQuestion.size()) {
            throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage(messageUtil.getLocalMessage("Bạn chưa khảo sát hết cau hỏi"));
        }
        studentAnswerRepository.saveAll(studentAnswerList);
        return true;
    }


}