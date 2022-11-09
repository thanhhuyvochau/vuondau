package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.ISubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements ISubjectService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectResponse createNewSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setCode(subjectRequest.getCode());
        subject.setName(subject.getName());
        List<Course> allCourse = courseRepository.findAllById(subjectRequest.getCourseIds());
//        subject.setCourses(allCourse);

        Subject subjectSaved = subjectRepository.save(subject);
        SubjectResponse response = new SubjectResponse();
        response.setId(subjectSaved.getId());
        response.setName(subjectSaved.getName());
        response.setCode(subjectSaved.getCode());
//        response.setCourseIds(subjectSaved.getCourses());

        return response;
    }

    @Override
    public SubjectResponse updateSubject(Long subjectId, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        subject.setCode(subjectRequest.getCode());
        subject.setName(subject.getName());
        List<Course> allCourse = courseRepository.findAllById(subjectRequest.getCourseIds());
//        subject.setCourses(allCourse);
        Subject subjectSaved = subjectRepository.save(subject);

        SubjectResponse response = new SubjectResponse();
        response.setId(subjectSaved.getId());
        response.setName(subjectSaved.getName());
        response.setCode(subjectSaved.getCode());
//        response.setCourseIds(subjectSaved.getCourses());
        return response;
    }

    @Override
    public Long deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        subjectRepository.delete(subject);
        return subjectId;
    }

    @Override
    public SubjectResponse getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setCode(subject.getCode());
//        response.setCourseIds(subject.getCourses());
        return response;
    }
}
