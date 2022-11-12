package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.request.StudentSurveyRequest;
import fpt.capstone.vuondau.entity.request.SurveyQuestionRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;

import java.util.List;
import java.util.Optional;

public interface ISurveyQuestionService {

    Boolean adminCreateSurveyQuestion(List<SurveyQuestionRequest> surveyQuestionRequest);

    Boolean studentSubmitSurvey( Long studentId,List<StudentSurveyRequest> studentSurveyRequests);
}
