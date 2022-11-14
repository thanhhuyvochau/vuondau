package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.CourseRequest;
import fpt.capstone.vuondau.entity.request.TopicsSubjectRequest;
import fpt.capstone.vuondau.entity.response.CourseResponse;

import java.util.List;

public interface ICourseService {
    TopicsSubjectRequest teacherCreateTopicInSubject( Long teacherId  ,TopicsSubjectRequest topicsSubjectRequest);
//    List<CourseResponse> getAll();
//
//    List<CourseResponse> searchCourseByName(String name);
//
//    CourseResponse create(CourseRequest courseRequest);
//
//    CourseResponse update(CourseRequest courseRequest, Long id);
//
//    Boolean delete(Long id);
}
