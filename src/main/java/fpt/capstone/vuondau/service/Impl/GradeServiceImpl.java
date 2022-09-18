package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.GradeRequest;
import fpt.capstone.vuondau.entity.response.GradeResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.GradeRepository;
import fpt.capstone.vuondau.service.IGradeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GradeServiceImpl implements IGradeService {
    private final CourseRepository courseRepository;

    private final GradeRepository gradeRepository ;

    public GradeServiceImpl(CourseRepository courseRepository, GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeResponse createNewGrade(GradeRequest gradeRequest) {
        Grade grade = new Grade() ;
        grade.setCode(gradeRequest.getCode());
        grade.setName(gradeRequest.getName());
        List<Course> allCourse = courseRepository.findAllById(gradeRequest.getCourseIds());
        grade.setCourses(allCourse);

        Grade gradeSaved = gradeRepository.save(grade);
        GradeResponse response = new GradeResponse();
        response.setId(gradeSaved.getId());
        response.setName(gradeSaved.getName());
        response.setCode(gradeSaved.getCode());
        response.setCourseIds(gradeSaved.getCourses());
        return response;
    }

    @Override
    public GradeResponse updateGrade(Long gradeId, GradeRequest gradeRequest) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay grade"));
        grade.setCode(gradeRequest.getCode());
        grade.setName(gradeRequest.getName());
        List<Course> allCourse = courseRepository.findAllById(gradeRequest.getCourseIds());
        grade.setCourses(allCourse);
        Grade gradeSaved = gradeRepository.save(grade);
        GradeResponse response = new GradeResponse();
        response.setId(gradeSaved.getId());
        response.setName(gradeSaved.getName());
        response.setCode(gradeSaved.getCode());
        response.setCourseIds(gradeSaved.getCourses());
        return response;

    }

    @Override
    public Long deleteGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay grade"));
        gradeRepository.delete(grade);
        return gradeId ;
    }

    @Override
    public GradeResponse getGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay grade"));
        GradeResponse response = new GradeResponse();
        response.setId(grade.getId());
        response.setName(grade.getName());
        response.setCode(grade.getCode());
        response.setCourseIds(grade.getCourses());
        return response;
    }
}
