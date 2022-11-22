package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleBaseResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.S1BaseSingleResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.request.CreateCourseRequest;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IClassService;
import fpt.capstone.vuondau.util.RequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClassServiceImpl implements IClassService {

    final private RequestUtil requestUtil;
    private final AccountRepository accountRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;

    private final CourseRepository courseRepository;

    private final MoodleCourseRepository moodleCourseRepository;

    public ClassServiceImpl(RequestUtil requestUtil, AccountRepository accountRepository, SubjectRepository subjectRepository, ClassRepository classRepository, CourseRepository courseRepository, MoodleCourseRepository moodleCourseRepository) {
        this.requestUtil = requestUtil;
        this.accountRepository = accountRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.moodleCourseRepository = moodleCourseRepository;
    }


    @Override
    public Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) throws JsonProcessingException {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher" + teacherId));



        // set class bên vườn đậu
        Class clazz = new Class();
        clazz.setName(createClassRequest.getName());
        clazz.setCode(createClassRequest.getCode());
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setNumberStudent(createClassRequest.getNumberStudent());
        clazz.setMaxNumberStudent(createClassRequest.getMaxNumberStudent());
        clazz.setStatus(EClassStatus.NOTSTART);
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setActive(false);
        clazz.setAccount(teacher);


        CreateCourseRequest createCourseRequest = createClassRequest.getCourseRequest();
        Subject subject = subjectRepository.findById(createCourseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));


        //set course chp class
        Course course = new Course();
        course.setName(createClassRequest.getCourseRequest().getName());
        course.setCode(createClassRequest.getCourseRequest().getCode());
        course.setTitle(createClassRequest.getCourseRequest().getTitle());
        course.setDescription(createClassRequest.getCourseRequest().getDescription());

        List<TeacherCourse> teacherCourseList = new ArrayList<>();
        TeacherCourse teacherCourse = new TeacherCourse();
        TeacherCourseKey teacherCourseKey = new TeacherCourseKey();
        teacherCourseKey.setTeachId(teacherId);
        teacherCourseKey.setCourseId(course.getId());
        teacherCourse.setId(teacherCourseKey);
        teacherCourse.setAccount(teacher);
        teacherCourse.setCourse(course);
        teacherCourseList.add(teacherCourse);
        course.setTeacherCourses(teacherCourseList);
        course.setIsActive(false);
        course.setSubject(subject);

        courseRepository.save(course);
//        teacher.setTeacherCourses(teacherCourseList);

        clazz.setCourse(course);
        classRepository.save(clazz);


        // set class từ vườn đậu moodle (source)

        S1CourseRequest s1CourseRequest = new S1CourseRequest();

        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();


        MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
        moodleCourseBody.setFullname(createClassRequest.getName());
        moodleCourseBody.setShortname(createClassRequest.getCode());
        moodleCourseBody.setCategoryid(subject.getId());
        moodleCourseBody.setStartdate(Instant.now().getEpochSecond());
        moodleCourseBody.setEnddate(Instant.now().getEpochSecond());
        moodleCourseBodyList.add(moodleCourseBody);


        s1CourseRequest.setCourses(moodleCourseBodyList);

        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);

        return true;
    }

    @Override
    public Boolean synchronizedClassToMoodle(MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {


        S1CourseRequest s1CourseRequest = new S1CourseRequest();

        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();


        for (MoodleCourseDataRequest.MoodleCourseBody request : moodleCourseDataRequest.getCourses()) {
            MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
            moodleCourseBody.setFullname(request.getFullname());
            moodleCourseBody.setShortname(request.getShortname());
            moodleCourseBody.setCategoryid(request.getCategoryid());
            moodleCourseBodyList.add(moodleCourseBody);
        }

        s1CourseRequest.setCourses(moodleCourseBodyList);

        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);

        return true;
    }

//    @Override
//    public  List<MoodleClassResponse>  synchronizedClass() throws JsonProcessingException {
//        MoodleMasterDataRequest s1MasterDataRequest = new MoodleMasterDataRequest() ;
//        List<MoodleClassResponse> response  = moodleCourseRepository.getCourse(s1MasterDataRequest);
//        List<MoodleClassResponse> classList = new ArrayList<>() ;
//        List<MoodleClassResponse> listClassMoodle = response.stream().map(moodleClassResponse -> {
//            MoodleClassResponse moodleClassResponse1 = new MoodleClassResponse() ;
//            moodleClassResponse1.setId(moodleClassResponse.getId());
//            classList.add(moodleClassResponse1 );
//            return moodleClassResponse1 ;
//        }).collect(Collectors.toList()) ;
//        return classList;
//    }
//


}
