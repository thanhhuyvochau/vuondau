package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.StudentFeedbackRequest;
import fpt.capstone.vuondau.entity.response.StudentFeedbackResponse;

import java.util.List;

public interface IFeedbackService {


    List<StudentFeedbackResponse> studentFeedbackTeacher(List<StudentFeedbackRequest> studentFeedbackRequest);

    List<Long> studentGetTeacherNeededFeedback();
}
