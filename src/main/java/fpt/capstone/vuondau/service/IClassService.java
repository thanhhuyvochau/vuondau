package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.common.EClassType;
import fpt.capstone.vuondau.entity.dto.ClassDetailDto;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.ClassStudentDto;
import fpt.capstone.vuondau.entity.dto.StudentDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IClassService {


    Boolean teacherRequestCreateClass(CreateClassRequest createClassRequest) throws JsonProcessingException;

    Boolean synchronizedClassToMoodle(MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException;

    ClassDto adminApproveRequestCreateClass(Long id) throws JsonProcessingException;


    ApiPage<ClassDto> getClassRequesting(ClassSearchRequest query, Pageable pageable);

    Boolean studentEnrollClass(Long studentId, Long classId);


    List<ClassDto> studentWaitingApproveIntoClass(Long classId);

    List<ClassStudentDto> getStudentWaitingIntoClass(Long classId);

    ApiPage<ClassDto> searchClass(ClassSearchRequest query,Pageable pageable);


    ClassDetailDto classDetail(Long id) throws JsonProcessingException;

    ApiPage<ClassDto> getAllClass(Pageable pageable);
    ApiPage<ClassDto> getAllClassForUser(Pageable pageable, EClassStatus classStatus);
    ApiPage<ClassDto> accountFilterClass(ClassSearchRequest query, Pageable pageable);

    ApiPage<ClassDto> classSuggestion(long infoFindTutorId, Pageable pageable);

    Boolean createClassForRecruiting(CreateClassRequest createClassRequest) throws JsonProcessingException;

    Boolean applyToRecruitingClass(Long classId);

    AccountResponse chooseCandicateForClass(ClassCandicateRequest request);

    ApiPage<CandicateResponse> getClassCandicate(Long classId, Pageable pageable);

    ApiPage<ClassDto> getRecruitingClasses(Pageable pageable);

    ApiPage<ClassDto> getClassByAccount( Pageable pageable);

    ClassDetailDto accountGetClassDetail(Long id);

    ClassResourcesResponse accountGetResourceOfClass(Long id);

    ApiPage<AccountResponse> accountGetStudentOfClass(Long id, Pageable pageable);

    List<ClassTimeTableResponse> accountGetTimeTableOfClass(Long id);

    ClassTeacherResponse studentGetTeacherInfoOfClass(Long id);
}
