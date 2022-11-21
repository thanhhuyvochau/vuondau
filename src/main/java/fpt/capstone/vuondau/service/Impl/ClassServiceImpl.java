package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IClassService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements IClassService {
    private final AccountRepository accountRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;

    public ClassServiceImpl(AccountRepository accountRepository, SubjectRepository subjectRepository, ClassRepository classRepository) {
        this.accountRepository = accountRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
    }

    @Override
    public Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher" + teacherId));
        Class clazz = new Class() ;
        clazz.setName(createClassRequest.getName());
        clazz.setCode(createClassRequest.getCode());
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setNumberStudent(createClassRequest.getNumberStudent());
        clazz.setMaxNumberStudent(createClassRequest.getMaxNumberStudent());
        clazz.setActive(false);
        clazz.setAccount(teacher);

        CreateCourseRequest createCourseRequest = createClassRequest.getCourseRequest();
        Course course = new Course() ;
        course.setCode(createCourseRequest.getCode());
        course.setTitle(createCourseRequest.getTitle());
        course.setDescription(createCourseRequest.getDescription());
        course.setIsActive(false);
        Subject subject = subjectRepository.findById(createCourseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        course.setSubject(subject);
        clazz.setCourse(course);

        classRepository.save(clazz) ;
        return true;
    }
}
