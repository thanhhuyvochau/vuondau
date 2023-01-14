package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.request.StudentFeedbackRequest;
import fpt.capstone.vuondau.entity.response.StudentFeedbackResponse;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.FeedbackRepository;
import fpt.capstone.vuondau.service.IFeedbackService;

import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final SecurityUtil securityUtil;

    private final ClassRepository classRepository ;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, SecurityUtil securityUtil, ClassRepository classRepository) {
        this.feedbackRepository = feedbackRepository;
        this.securityUtil = securityUtil;
        this.classRepository = classRepository;
    }

    @Override
    public List<StudentFeedbackResponse> studentFeedbackTeacher(List<StudentFeedbackRequest> studentFeedbackRequest) {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();
        List<Long> classIdsList = getClassesNeedFeedbackOfStudent(student);
        List<Class> classList = classRepository.findAllById(classIdsList);

        List<FeedBack> feedBackList = new ArrayList<>();
        for (StudentFeedbackRequest feedbackRequest : studentFeedbackRequest) {
            if (feedbackRequest.getPoint() < 1 ||feedbackRequest.getPoint() > 6 ) {
                throw ApiException.create(HttpStatus.CONFLICT)
                        .withMessage("Vui lòng chỉ đánh giá giáo viên từ 1 đến 5 sao");
            }
            Long teacherId = feedbackRequest.getTeacherId();
            for (Class aClass : classList) {
                if (aClass.getAccount()!=null){
                    if (aClass.getAccount().getId().equals(teacherId)){
                        FeedBack feedBack = new FeedBack() ;
                        feedBack.setTeacherId(aClass.getAccount().getId());
                        StudentClassKey studentClassKey = new StudentClassKey() ;
                        studentClassKey.setClassId(aClass.getId());
                        studentClassKey.setStudentId(student.getId());
                        feedBack.setStudentClassKeyId(studentClassKey);
                        feedBack.setPointEvaluation(feedbackRequest.getPoint());
                        feedBack.setContent(feedbackRequest.getContent());
                        feedBackList.add(feedBack) ;
                    }
                }
                else if (!aClass.getAccount().equals(teacherId)){
                    throw ApiException.create(HttpStatus.CONFLICT)
                            .withMessage("Bạn không thể đánh giá giáo viên này!!");
                }
            }
        }
        feedbackRepository.saveAll(feedBackList) ;
        return feedBackList.stream().map(this::doConvertFeedbackToStudentFeedbackResponse).collect(Collectors.toList());
    }

    @Override
    public List<Long> studentGetTeacherNeededFeedback() {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();
        List<Long> classIds = getClassesNeedFeedbackOfStudent(student);
        List<Class> classList = classRepository.findAllById(classIds);
        return  classList.stream().map(aClass -> aClass.getAccount().getId()).collect(Collectors.toList());

    }

    private StudentFeedbackResponse doConvertFeedbackToStudentFeedbackResponse(FeedBack feedBack){
        StudentFeedbackResponse  studentFeedbackResponse = new StudentFeedbackResponse() ;
        StudentClassKey studentClassKeyId = feedBack.getStudentClassKeyId();
        studentFeedbackResponse.setStudentId(studentClassKeyId.getStudentId());
        studentFeedbackResponse.setTeacherId(feedBack.getTeacherId());
        studentFeedbackResponse.setClassId(studentClassKeyId.getClassId());
        studentFeedbackResponse.setContent(feedBack.getContent());
        studentFeedbackResponse.setPoint(feedBack.getPointEvaluation());
        return studentFeedbackResponse ;
    }

    private List<Long> getClassesNeedFeedbackOfStudent (  Account student ) {
        List<Class> classList = new ArrayList<>();

        List<StudentClass> studentClasses = student.getStudentClasses();
        for (StudentClass studentClass : studentClasses) {
            Class aClass = studentClass.getaClass();
            if (aClass.getStatus().equals(EClassStatus.STARTING)) {
                List<TimeTable> timeTables = aClass.getTimeTables();
                for (TimeTable timeTable : timeTables) {
                    if (timeTable != null) {
                        TimeTable timeTable1 = timeTables.get(timeTables.size() - 4);
                        Instant date = timeTable1.getDate();
                        if (date.isBefore(Instant.now()) || date.equals(Instant.now())) {
                            classList.add(aClass);
                        }
                    }
                }
            }

        }
        return classList.stream().map(Class::getId).distinct().collect(Collectors.toList());
    }
}
