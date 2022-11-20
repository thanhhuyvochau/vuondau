package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
    TopicsSubjectRequest teacherCreateTopicInSubject( Long teacherId  ,TopicsSubjectRequest topicsSubjectRequest);

    ClassSubjectResponse createRegisterSubject(Long teacherId,  Long subjectId, ClassRequest classRequest );

    ApiPage<CourseResponse> searchCourse(CourseSearchRequest query, Pageable pageable);

    ApiPage<CourseResponse> viewAllCourse(Pageable pageable);

    CourseDetailResponse viewCourseDetail(long courseID);

    CourseDetailResponse updateCourse(long courseID, CourseRequest subjectRequest);


    List<ClassCourseResponse> viewHistoryCourse(long studentId);

    ClassCourseResponse studentEnrollCourse(long id, long courseId, long classId);
}
