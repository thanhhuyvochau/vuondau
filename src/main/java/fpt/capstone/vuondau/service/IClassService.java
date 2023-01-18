package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.moodle.request.CreateCourseRequest;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.ClassDetailDto;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.ClassStudentDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;


import java.text.ParseException;
import java.util.List;

public interface IClassService {


    Long teacherRequestCreateClass(TeacherCreateClassRequest createClassRequest) throws JsonProcessingException, ParseException;

    Long teacherRequestCreateClassSubjectCourse(Long id, CreateClassSubjectRequest createClassRequest);

    ClassDto adminApproveRequestCreateClass(Long id) throws JsonProcessingException;


    ApiPage<ClassDto> getClassRequesting(ClassSearchRequest query, Pageable pageable);

    Boolean studentEnrollClass(Long studentId, Long classId);

    List<ClassStudentDto> getStudentWaitingIntoClass(Long classId);

    ApiPage<ClassDto> searchClass(ClassSearchRequest query, Pageable pageable);


    ClassDto classDetail(Long id) throws JsonProcessingException;

    ApiPage<ClassDto> getAllClass(Pageable pageable);

    ApiPage<ClassDto> getAllClassForUser(Pageable pageable, GuestSearchClassRequest guestSearchClassRequest);

    ApiPage<ClassDto> accountFilterClass(ClassSearchRequest query, Pageable pageable);

    ApiPage<ClassDto> classSuggestion(long infoFindTutorId, Pageable pageable);

    Long createClassForRecruiting(CreateClassRequest createClassRequest) throws JsonProcessingException, ParseException;

    Boolean applyToRecruitingClass(Long classId);

    AccountResponse chooseCandicateForClass(ClassCandicateRequest request);

    ApiPage<CandicateResponse> getClassCandicate(Long classId, Pageable pageable);

    ApiPage<ClassDto> getRecruitingClasses(Pageable pageable);

    ApiPage<ClassDto> getClassByAccount(ClassSearchRequest request, Pageable pageable);

    ClassDetailDto accountGetClassDetail(Long id);

    ClassResourcesResponse accountGetResourceOfClass(Long id);

    ApiPage<AccountResponse> accountGetStudentOfClass(Long id, Pageable pageable);

    List<ClassTimeTableResponse> accountGetTimeTableOfClass(Long id);

    ClassTeacherResponse studentGetTeacherInfoOfClass(Long id);


    ClassAttendanceResponse accountGetAttendanceOfClass(Long id);

    ApiPage<ClassDto> adminGetClass(EClassStatus status, Pageable pageable);

    List<ClassDto> getClassByAccountAsList(EClassStatus status);

    ClassDto confirmAppreciation(Long id) throws JsonProcessingException;

    Long updateClassForRecruiting(Long id, CreateClassRequest createClassRequest) throws ParseException;
    ClassDto adminRejectRequestCreateClass(Long id) throws JsonProcessingException;

    Boolean adminEnrolStudentIntoClass(Long studentId, Long classId) throws JsonProcessingException;
}
