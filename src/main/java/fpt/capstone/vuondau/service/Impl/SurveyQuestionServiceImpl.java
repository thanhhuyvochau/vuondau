package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Answer;
import fpt.capstone.vuondau.entity.StudentAnswer;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EQuestionType;
import fpt.capstone.vuondau.entity.SurveyQuestion;
import fpt.capstone.vuondau.entity.dto.AnswerDto;
import fpt.capstone.vuondau.entity.dto.SurveyQuestionAnswerDto;
import fpt.capstone.vuondau.entity.request.StudentSurveyRequest;
import fpt.capstone.vuondau.entity.request.SurveyQuestionRequest;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.AnswerRepository;
import fpt.capstone.vuondau.repository.StudentAnswerRepository;
import fpt.capstone.vuondau.repository.SurveyQuestionRepository;
import fpt.capstone.vuondau.service.ISurveyQuestionService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        // Lưu db to suggest subject


        studentAnswerRepository.saveAll(studentAnswerList);
        return true;
    }

    @Override
    public List<SurveyQuestionAnswerDto> listSurveyQuestion() {
        List<SurveyQuestion> allSurveyQuestion = surveyQuestionRepository.findAll();
        List<SurveyQuestionAnswerDto> questionAnswerDtoList = new ArrayList<>();
        allSurveyQuestion.stream().map(surveyQuestion -> {
            SurveyQuestionAnswerDto surveyQuestionAnswer = new SurveyQuestionAnswerDto();
            surveyQuestionAnswer.setQuestionId(surveyQuestion.getId());
            surveyQuestionAnswer.setQuestion(surveyQuestion.getQuestion());


            List<AnswerDto> answerDtoList = new ArrayList<>() ;
            List<Answer> answers = surveyQuestion.getAnswers();
                answers.stream().map(answer -> {
                    AnswerDto answerDto = ObjectUtil.copyProperties(answer , new AnswerDto() , AnswerDto.class) ;
                    answerDtoList.add(answerDto) ;
                    return answerDto ;
                }).collect(Collectors.toList());
            surveyQuestionAnswer.setAnswersMultipleChoice(answerDtoList);
            surveyQuestionAnswer.setQuestionType(surveyQuestion.getQuestionType());
            questionAnswerDtoList.add(surveyQuestionAnswer);
            return surveyQuestion ;
         }).collect(Collectors.toList());

        return questionAnswerDtoList;
    }


}
