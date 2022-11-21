package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.ClassCourseResponse;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IClassService {


    Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest);

}
