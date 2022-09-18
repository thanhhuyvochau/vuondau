package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.CourseRequest;
import fpt.capstone.vuondau.entity.response.CourseResponse;

import java.util.List;

public interface ICourseService {
    List<CourseResponse> getAll();

    List<CourseResponse> searchCourseByName(String name);

    CourseResponse create(CourseRequest courseRequest);

    CourseResponse update(CourseRequest courseRequest, Long id);

    Boolean delete(Long id);
}
