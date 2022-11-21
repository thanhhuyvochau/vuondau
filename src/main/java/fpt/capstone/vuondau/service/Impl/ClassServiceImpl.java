package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.S1BaseSingleResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.S1CourseResponse;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.request.CreateCourseRequest;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IClassService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClassServiceImpl implements IClassService {
    private final AccountRepository accountRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;

    private final CourseRepository courseRepository;

    private final MoodleCourseRepository moodleCourseRepository;

    public ClassServiceImpl(AccountRepository accountRepository, SubjectRepository subjectRepository, ClassRepository classRepository, CourseRepository courseRepository, MoodleCourseRepository moodleCourseRepository) {
        this.accountRepository = accountRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.moodleCourseRepository = moodleCourseRepository;
    }


    @Override
    public Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher" + teacherId));

        Class clazz = new Class();
        clazz.setName(createClassRequest.getName());
        clazz.setCode(createClassRequest.getCode());
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setNumberStudent(createClassRequest.getNumberStudent());
        clazz.setMaxNumberStudent(createClassRequest.getMaxNumberStudent());
        clazz.setStatus(EClassStatus.NOTSTART);

        clazz.setActive(false);
        clazz.setAccount(teacher);


        CreateCourseRequest createCourseRequest = createClassRequest.getCourseRequest();
//        Course course = new Course() ;
//        course.setCode(createCourseRequest.getCode());
//        course.setTitle(createCourseRequest.getTitle());
//        course.setDescription(createCourseRequest.getDescription());

        Course course = new Course();
        course.setName(createClassRequest.getCourseRequest().getName());
        course.setCode(createClassRequest.getCourseRequest().getCode());
        course.setTitle(createClassRequest.getCourseRequest().getTitle());
        course.setDescription(createClassRequest.getCourseRequest().getDescription());

        course.setIsActive(false);
        Subject subject = subjectRepository.findById(createCourseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        course.setSubject(subject);

        courseRepository.save(course);

        clazz.setCourse(course);
        classRepository.save(clazz);

        S1CourseRequest s1CourseRequest = new S1CourseRequest();

        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();

        MoodleCourseDataRequest.MoodleCourseBody courseBody = new MoodleCourseDataRequest.MoodleCourseBody();
        courseBody.setFullname(createClassRequest.getName());
        courseBody.setShortname(createClassRequest.getCode());
        courseBody.setCategoryid(subject.getId());

        moodleCourseBodyList.add(courseBody);

        s1CourseRequest.setCourses(moodleCourseBodyList);
        S1BaseSingleResponse<S1CourseResponse> s1CourseResponseS1BaseSingleResponse = moodleCourseRepository.postCourse(s1CourseRequest);


        return true;
    }

    @Override
    public Boolean synchronizedClassToMoodle( MoodleCourseDataRequest moodleCourseDataRequest ) {


        List<MoodleCourseDataRequest>moodleCourseDataRequestList = new ArrayList<>() ;
        S1CourseRequest s1CourseRequest = new S1CourseRequest() ;

        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>() ;


        for (MoodleCourseDataRequest.MoodleCourseBody request : moodleCourseDataRequest.getCourses()) {
            MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
            moodleCourseBody.setFullname(request.getFullname());
            moodleCourseBody.setShortname(request.getShortname());
            moodleCourseBody.setCategoryid(request.getCategoryid());
            moodleCourseBodyList.add(moodleCourseBody) ;
        }

        s1CourseRequest.setCourses(moodleCourseBodyList);

        S1BaseSingleResponse<S1CourseResponse> s1CourseResponseS1BaseSingleResponse = moodleCourseRepository.postCourse(s1CourseRequest);

//        moodleCourseRepository.postCourse(s1CourseRequest);

        return true ;
    }


//    public Boolean synchronizedCourseToMoodle (CreateClassRequest createClassRequest ){
//
//        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>() ;
//
//        CreateCourseRequest courseRequest = createClassRequest.getCourseRequest();
//        for (CreateCourseRequest  request : courseRequest) {
//            MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
//            moodleCourseBody.setFullname(request.getFullname());
//            moodleCourseBody.setShortname(request.getShortname());
//            moodleCourseBody.setCategoryid(request.getCategoryid());
//            moodleCourseBodyList.add(moodleCourseBody) ;
//        }
//
//
//
//
//        s1CourseRequest.setCourses(moodleCourseBodyList);
//
//        S1BaseSingleResponse<S1CourseResponse> s1CourseResponseS1BaseSingleResponse = moodleCourseRepository.postCourse(s1CourseRequest);
//
////        moodleCourseRepository.postCourse(s1CourseRequest);
//
//        S1BaseSingleResponse<S1CourseResponse> s1CourseResponseS1BaseSingleResponse = moodleCourseRepository.postCourse(s1CourseRequest);
//
//        return true ;
//    }

}
