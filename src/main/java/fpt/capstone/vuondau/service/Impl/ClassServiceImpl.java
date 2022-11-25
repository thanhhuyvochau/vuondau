package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.ClassSearchRequest;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IClassService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.RequestUtil;
import fpt.capstone.vuondau.util.specification.ClassSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    private final StudentClassRepository studentClassRepository ;
    private final MessageUtil messageUtil;

    public ClassServiceImpl(RequestUtil requestUtil, AccountRepository accountRepository, SubjectRepository subjectRepository, ClassRepository classRepository, CourseRepository courseRepository, MoodleCourseRepository moodleCourseRepository, StudentClassRepository studentClassRepository, MessageUtil messageUtil) {
        this.requestUtil = requestUtil;
        this.accountRepository = accountRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.moodleCourseRepository = moodleCourseRepository;
        this.studentClassRepository = studentClassRepository;
        this.messageUtil = messageUtil;
    }


    @Override
    public Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) throws JsonProcessingException {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher" + teacherId));


        // set class bên vườn đậu
        Class clazz = new Class();
        clazz.setName(createClassRequest.getName());
        if (classRepository.existsByCode(createClassRequest.getCode())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class code da ton tai"));
        }

        clazz.setCode(createClassRequest.getCode());
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setNumberStudent(createClassRequest.getNumberStudent());
        clazz.setMaxNumberStudent(createClassRequest.getMaxNumberStudent());
        clazz.setStatus(EClassStatus.REQUESTING);
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setActive(false);

        clazz.setAccount(teacher);


//        CreateCourseRequest createCourseRequest = createClassRequest.getCourseRequest();
        Subject subject = subjectRepository.findById(createClassRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));


        //set course chp class
//        if (createClassRequest.getCourseRequest()!= null){
//            Course course = new Course();
//            course.setName(createClassRequest.getCourseRequest().getName());
//            course.setCode(createClassRequest.getCourseRequest().getCode());
//            course.setTitle(createClassRequest.getCourseRequest().getTitle());
//            course.setDescription(createClassRequest.getCourseRequest().getDescription());
//            List<TeacherCourse> teacherCourseList = new ArrayList<>();
//            TeacherCourse teacherCourse = new TeacherCourse();
//            TeacherCourseKey teacherCourseKey = new TeacherCourseKey();
//            teacherCourseKey.setTeachId(teacherId);
//            teacherCourseKey.setCourseId(course.getId());
//            teacherCourse.setId(teacherCourseKey);
//            teacherCourse.setAccount(teacher);
//            teacherCourse.setCourse(course);
//            teacherCourseList.add(teacherCourse);
//            course.setTeacherCourses(teacherCourseList);
//            course.setIsActive(false);
//            course.setSubject(subject);
//
//            courseRepository.save(course);
//            clazz.setCourse(course);
//        }


//        teacher.setTeacherCourses(teacherCourseList);


        classRepository.save(clazz);


        // set class từ vườn đậu moodle (source)

//        S1CourseRequest s1CourseRequest = new S1CourseRequest();
//
//        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();
//
//
//        MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
//        moodleCourseBody.setFullname(createClassRequest.getName());
//        moodleCourseBody.setShortname(createClassRequest.getCode());
//        moodleCourseBody.setCategoryid(subject.getCategoryMoodleId());
//        moodleCourseBody.setStartdate(Instant.now().getEpochSecond());
//        moodleCourseBody.setEnddate(Instant.now().getEpochSecond());
//        moodleCourseBodyList.add(moodleCourseBody);
//
//
//        s1CourseRequest.setCourses(moodleCourseBodyList);
//
//        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);

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

    @Override
    public ClassDto adminApproveRequestCreateClass(Long id) throws JsonProcessingException {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + id));
        if (!aClass.getStatus().equals(EClassStatus.REQUESTING)) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class khong phai trang thai de Active"));
        }


        aClass.setActive(true);
        aClass.setStatus(EClassStatus.NOTSTART);
        Class save = classRepository.save(aClass);

        S1CourseRequest s1CourseRequest = new S1CourseRequest();
        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();

        MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
        moodleCourseBody.setFullname(save.getName());
        moodleCourseBody.setShortname(save.getCode());
        Course course = save.getCourse();
        if (course != null) {
            moodleCourseBody.setCategoryid(course.getSubject().getCategoryMoodleId());
        }
        if (save.getStartDate()!= null) {
            moodleCourseBody.setStartdate(save.getStartDate().toEpochMilli());
        }
        if (save.getEndDate()!= null) {
            moodleCourseBody.setEnddate(save.getEndDate().toEpochMilli());
        }
        moodleCourseBodyList.add(moodleCourseBody);
        s1CourseRequest.setCourses(moodleCourseBodyList);

        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);


        ClassDto classDto = ObjectUtil.copyProperties(save, new ClassDto(), ClassDto.class);


        return classDto;
    }

    @Override
    public ApiPage<ClassDto> getClassRequesting(ClassSearchRequest query, Pageable pageable) {
        ClassSpecificationBuilder builder = ClassSpecificationBuilder.specification()
                .queryStatusClass(query.getStatus());

        Page<Class> classesPage = classRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(classesPage.map(this::convertClassToClassResponse));


    }

    @Override
    public Boolean studentEnrollClass(Long studentId, Long classId) {
        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay student" + studentId));

        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));

        List<StudentClass> studentClasses = student.getStudentClasses();
        studentClasses.stream().map(studentClass -> {
            if (studentClass.getaClass().equals(aClass)){
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("student dang trong class nay roi"));
            }
            return studentClass ;
        }).collect(Collectors.toList());

        StudentClass studentClass = new StudentClass() ;
        StudentClassKey key = new StudentClassKey() ;
        key.setClassId(aClass.getId());
        key.setStudentId(studentId);

        studentClass.setId(key);
        studentClass.setAClass(aClass);
        studentClass.setAccount(student);
        studentClass.setIs_enrolled(false);
//        Long numberStudent = aClass.getNumberStudent();
//        aClass.setNumberStudent(numberStudent  + 1);
        student.getStudentClasses().add(studentClass) ;
        accountRepository.save(student) ;


        return true;
    }

    @Override
    public List<ClassStudentDto> getStudentWaitingIntoClass(Long classId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));
        List<StudentClass> studentClasses = aClass.getStudentClasses();

        ClassStudentDto classStudentDto = new ClassStudentDto();
        List<ClassStudentDto>classStudentDtos = new ArrayList<>() ;
        classStudentDto.setClassId(classId);
        List<StudentDto> studentList = new ArrayList<>() ;
        studentClasses.stream().map(studentClass -> {
            StudentDto studentDto = new StudentDto();
            Account account = studentClass.getAccount();
            if (account!= null){
                studentDto  = ObjectUtil.copyProperties(account, new StudentDto() , StudentDto.class);
                studentDto.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
//                studentDto.setPhoneNumber(account.getPhoneNumber());
//                studentDto.setBirthday();
            }

            studentList.add(studentDto) ;
            return studentClass ;
        }).collect(Collectors.toList());

        classStudentDto.setStudents(studentList);
        classStudentDtos.add(classStudentDto);
        return classStudentDtos;
    }

    @Override
    public List<ClassDto> searchClass(ClassSearchRequest query) {
        ClassSpecificationBuilder builder = ClassSpecificationBuilder.specification()
                .queryLike(query.getQ()) ;

        List<Class> classList = classRepository.findAll(builder.build());
        List<ClassDto>  classDtoList = new ArrayList<>() ;
        classList.stream().map(aClass -> {
            ClassDto classDto= ObjectUtil.copyProperties(aClass, new ClassDto() , ClassDto.class) ;
            classDtoList.add(classDto) ;
            return aClass ;
        }).collect(Collectors.toList());
        return classDtoList;
    }

    @Override
    public ClassDetailDto classDetail(Long id) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + id));
        ClassDetailDto classDetail = ObjectUtil.copyProperties(aClass, new ClassDetailDto(), ClassDetailDto.class) ;

        Course course = aClass.getCourse();
        if (course!= null) {
            CourseResponse  courseResponse = new CourseResponse();
            courseResponse.setId(course.getId());
            courseResponse.setCourseName(course.getName());
            courseResponse.setCourseTitle(course.getTitle());
            if (course.getResource()!= null){
                courseResponse.setImage(course.getResource().getUrl());
            }
            courseResponse.setUnitPriceCourse(course.getUnitPrice());
            courseResponse.setFinalPriceCourse(course.getFinalPrice());
            Subject subject = course.getSubject();
            if (subject!= null) {
                courseResponse.setSubject(ObjectUtil.copyProperties(subject , new SubjectDto() , SubjectDto.class));
            }
            classDetail.setCourse(courseResponse);
        }


        Account account = aClass.getAccount();
        if (account!= null){
            AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class) ;
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
            Resource resource = account.getResource();
            if (resource!=null){
                accountResponse.setAvatar(resource.getUrl());
            }
            classDetail.setTeacher(accountResponse);
        }



        List<Account> studentList = aClass.getStudentClasses().stream().map(StudentClass::getAccount).collect(Collectors.toList());

        List<AccountResponse> accountResponses = new ArrayList<>() ;
        studentList.stream().map(studentMap -> {
            AccountResponse student = ObjectUtil.copyProperties(studentMap , new AccountResponse(), AccountResponse.class) ;
            student.setRole(ObjectUtil.copyProperties(studentMap.getRole(), new RoleDto(), RoleDto.class));
            if (studentMap.getRequests()!=null){
                student.setAvatar(studentMap.getResource().getUrl());
            }

            accountResponses.add(student) ;
            return studentMap ;
        }).collect(Collectors.toList()) ;
        classDetail.setStudents(accountResponses);


        return classDetail;
    }

    @Override
    public ApiPage<ClassDto> getAllClass( Pageable pageable) {
        Page<Class> classesPage = classRepository.findAll(pageable);

        return PageUtil.convert(classesPage.map(this::convertClassToClassResponse));

    }

    public ClassDto convertClassToClassResponse(Class aclass) {
        ClassDto classDto = ObjectUtil.copyProperties(aclass, new ClassDto(), ClassDto.class);
        return classDto;
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
