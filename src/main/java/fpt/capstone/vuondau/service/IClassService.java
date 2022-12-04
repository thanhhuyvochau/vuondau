package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ClassDetailDto;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.ClassStudentDto;
import fpt.capstone.vuondau.entity.dto.StudentDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IClassService {


    Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) throws JsonProcessingException;

    Boolean synchronizedClassToMoodle( MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException;

    ClassDto adminApproveRequestCreateClass(Long id) throws JsonProcessingException;


    ApiPage<ClassDto> getClassRequesting(ClassSearchRequest query , Pageable pageable);

    Boolean studentEnrollClass(Long studentId, Long classId);


    List<ClassDto> studentWaitingApproveIntoClass(Long classId);

    List<ClassStudentDto> getStudentWaitingIntoClass(Long classId);

    List<ClassDto> searchClass(ClassSearchRequest query);



    ClassDetailDto classDetail(Long id) throws JsonProcessingException;

    ApiPage<ClassDto> getAllClass( Pageable pageable);

    ApiPage<ClassDto> accountFilterClass(Long accountId, ClassSearchRequest query, Pageable pageable);
}
