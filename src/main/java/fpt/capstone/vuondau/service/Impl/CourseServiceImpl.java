package fpt.capstone.vuondau.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.ResourceMoodleResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.ClassCourseResponse;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ICourseService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.CourseSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherCourseRepository teacherCourseRepository;

    private final AccountRepository accountRepository;

    private final ClassRepository classRepository;

    private final MoodleCourseRepository moodleCourseRepository;

    private final MessageUtil messageUtil;


    public CourseServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, TeacherCourseRepository teacherCourseRepository, AccountRepository accountRepository, ClassRepository classRepository, MoodleCourseRepository moodleCourseRepository, MessageUtil messageUtil) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.teacherCourseRepository = teacherCourseRepository;
        this.accountRepository = accountRepository;
        this.classRepository = classRepository;
        this.moodleCourseRepository = moodleCourseRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public TopicsSubjectRequest teacherCreateTopicInSubject(Long teacherId, TopicsSubjectRequest topicsSubjectRequest) {

        Subject subject = subjectRepository.findById(topicsSubjectRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("subject not found with id:" + topicsSubjectRequest.getSubjectId()));
        Account account = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("teacher not found with id:" + teacherId));
        List<Course> courseList = new ArrayList<>();

        TopicsSubjectRequest response = new TopicsSubjectRequest();

        List<CourseDto> coursesRequest = topicsSubjectRequest.getCourses();
        for (CourseDto courseDto : coursesRequest) {
            Course course = new Course();
            course.setCode(courseDto.getCode());
            course.setName(courseDto.getName());
            course.setGrade(courseDto.getGrade());
            course.setSubject(subject);
            List<TeacherCourse> teacherCourseList = new ArrayList<>();
            TeacherCourse teacherCourse = new TeacherCourse();
            TeacherCourseKey teacherCourseKey = new TeacherCourseKey();
            teacherCourseKey.setTeachId(account.getId());
            teacherCourseKey.setCourseId(course.getId());
            teacherCourse.setId(teacherCourseKey);
            teacherCourse.setCourse(course);
            teacherCourse.setAccount(account);
            teacherCourseList.add(teacherCourse);
            course.setTeacherCourses(teacherCourseList);
            courseList.add(course);

            // Response
            response.setSubjectId(subject.getId());
            List<CourseDto> courseDtoList = new ArrayList<>();
            courseList.stream().map(courseResponse -> {
                CourseDto courseDtoResponse = ObjectUtil.copyProperties(courseResponse, new CourseDto(), CourseDto.class);
                courseDtoList.add(courseDtoResponse);
                return courseResponse;
            }).collect(Collectors.toList());
            response.setCourses(courseDtoList);
        }
        courseRepository.saveAll(courseList);
        return response;
    }

    @Override
    public ClassSubjectResponse createRegisterSubject(Long teacherId, Long subjectId, ClassRequest classRequest) {

        Account account = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("teacher not found with id:" + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("subject not found with id:" + subjectId));

        List<Long> courseIds = new ArrayList<>();


        Course course = new Course();
        course.setCode("introduce");
        course.setName("introduce");
        course.setIsActive(false);
        course.setSubject(subject);
        List<TeacherCourse> teacherCourseList = new ArrayList<>();
        TeacherCourse teacherCourse = new TeacherCourse();
        TeacherCourseKey teacherCourseKey = new TeacherCourseKey();
        teacherCourseKey.setTeachId(account.getId());
        teacherCourseKey.setCourseId(course.getId());
        teacherCourse.setId(teacherCourseKey);
        teacherCourse.setCourse(course);
        teacherCourse.setAccount(account);
        teacherCourseList.add(teacherCourse);
        course.setTeacherCourses(teacherCourseList);

        Course save = courseRepository.save(course);

        Class clazz = new Class();
        clazz.setCode(classRequest.getCode());
        clazz.setName(classRequest.getName());
        clazz.setStartDate(Instant.now());
        clazz.setEndDate(classRequest.getEndDate());
        clazz.setCourse(course);
        clazz.setAccount(account);
        clazz.setLevel(classRequest.getLevel());
        clazz.setNumberStudent(30L);
        clazz.setActive(false);

        Class classSave = classRepository.save(clazz);


        courseIds.add(save.getId());
        ClassSubjectResponse response = new ClassSubjectResponse();
        response.setId(subject.getId());
        response.setCode(save.getSubject().getCode());
        response.setName(subject.getName());
        response.setCourseIds(courseIds);

        ClassDto classDto = new ClassDto();
        classDto.setName(classSave.getName());
        classDto.setCode(classSave.getCode());

        classDto.setLevel(classSave.getLevel());
        classDto.setEndDate(classSave.getEndDate());
        classDto.setStartDate(classSave.getStartDate());
        classDto.setNumberStudent(classSave.getNumberStudent());
        response.setClazz(classDto);
        return response;
    }


    @Override
    public ApiPage<CourseResponse> searchCourse(CourseSearchRequest query, Pageable pageable) {
        CourseSpecificationBuilder builder = CourseSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Course> coursePage = courseRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(coursePage.map(this::convertCourseToCourseResponse));

    }

//    public CourseDetailResponse convertCourseToCourseResponse(Course course) {
//        CourseDetailResponse courseResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class);
//        courseResponse.setGrade(course.getGrade());
//        return courseResponse;
//    }


    @Override
    public ApiPage<CourseResponse> viewAllCourse(Pageable pageable) {
        Page<Course> allCourse = courseRepository.findAll(pageable);
        return PageUtil.convert(allCourse.map(this::convertCourseToCourseResponse));
    }

    private CourseResponse convertCourseToCourseResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getName());
        courseResponse.setUnitPriceCourse(course.getUnitPrice());
        courseResponse.setFinalPriceCourse(course.getFinalPrice());
        courseResponse.setCourseTitle(course.getTitle());
        List<TeacherCourse> teacherCourses = course.getTeacherCourses();
        teacherCourses.stream().map(teacherCourse -> {
            courseResponse.setTeacherName(teacherCourse.getAccount().getName());
            return teacherCourse;
        }).collect(Collectors.toList());
        courseResponse.setUnitPriceCourse(course.getUnitPrice());
        if (course.getResource() != null) {
            courseResponse.setImage(course.getResource().getUrl());
        }
        Subject subject = course.getSubject();
        if (subject != null) {
            courseResponse.setSubject(ObjectUtil.copyProperties(subject, new SubjectDto(), SubjectDto.class));
        }


        return courseResponse;
    }

    public CourseDetailResponse convertCourseToCourseDetailResponse(Course course) throws JsonProcessingException {
        CourseDetailResponse courseDetailResponse = new CourseDetailResponse();


        // set course
        // set course detail

        courseDetailResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class, true);
        if (course.getResource() != null) {
            courseDetailResponse.setImage(course.getResource().getUrl());
        }
        courseDetailResponse.setActive(course.getIsActive());
        courseDetailResponse.setUnitPrice(course.getUnitPrice());
        courseDetailResponse.setTitle(course.getTitle());

        // set subject
        Subject subject = course.getSubject();
        if (subject != null) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setId(subject.getId());
            subjectDto.setName(subject.getName());
            subjectDto.setCode(subject.getCode());
            courseDetailResponse.setSubject(subjectDto);
        }
        Class classes = null ;
        // set Class
        if (!course.getTeacherCourses().isEmpty()){
            try {
                Account account = course.getTeacherCourses().stream().map(TeacherCourse::getAccount).findFirst().get();
                classes = classRepository.findByCourseAndAccount(course, account);
                if (classes != null) {
                    ClassDto classDto = new ClassDto();
                    classDto.setId(classes.getId());
                    classDto.setName(classes.getName());
                    classDto.setCode(classes.getCode());
                    classDto.setLevel(classes.getLevel());
                    classDto.setStartDate(classes.getStartDate());
                    classDto.setEndDate(classes.getEndDate());
                    classDto.setNumberStudent(classes.getNumberStudent());
                    classDto.setMaxNumberStudent(classes.getMaxNumberStudent());

                    courseDetailResponse.setClazz(classDto);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        // set teacher course
        List<TeacherCourseDto> teacherCourseDtoList = new ArrayList<>();
        List<TeacherCourse> teacherCourses = course.getTeacherCourses();
        teacherCourses.stream().map(teacherCourse -> {
            TeacherCourseDto teacherCourseDto = new TeacherCourseDto();
            teacherCourseDto.setTopicId(teacherCourse.getCourse().getId());
            teacherCourseDto.setIsAllowed(teacherCourse.getIsAllowed());
            teacherCourseDto.setTeacherId(teacherCourse.getAccount().getId());
            teacherCourseDto.setTeacherName(teacherCourse.getAccount().getName());
            teacherCourseDtoList.add(teacherCourseDto);

            return teacherCourse;
        }).collect(Collectors.toList());

        courseDetailResponse.setTeacherCourse(teacherCourseDtoList);


        CourseIdRequest courseIdRequest = new CourseIdRequest();
        if (classes!= null) {
            courseIdRequest.setCourseid(15L);
            try {
                List<MoodleRecourseClassResponse> resourceCourse = moodleCourseRepository.getResourceCourse(courseIdRequest);

                List<MoodleRecourseClassResponse> moodleRecourseClassResponseList = new ArrayList<>();
                resourceCourse.stream().peek(moodleRecourseClassResponse -> {
                    MoodleRecourseClassResponse setResource = ObjectUtil.copyProperties(moodleRecourseClassResponse, new MoodleRecourseClassResponse() , MoodleRecourseClassResponse.class);

                    List<ResourceMoodleResponse> modules = moodleRecourseClassResponse.getModules();
                    List<ResourceMoodleResponse> resourceMoodleResponseList = new ArrayList<>() ;
                    modules.stream().peek(moodleResponse -> {

                        ResourceMoodleResponse resourceMoodleResponse = ObjectUtil.copyProperties(moodleResponse, new ResourceMoodleResponse(), ResourceMoodleResponse.class);

                        resourceMoodleResponseList.add(resourceMoodleResponse) ;
                    }).collect(Collectors.toList());

                    setResource.setModules(resourceMoodleResponseList);

                    moodleRecourseClassResponseList.add(setResource);
                }).collect(Collectors.toList());
                courseDetailResponse.setResources(moodleRecourseClassResponseList);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return courseDetailResponse;
    }
    @Override
    public CourseDetailResponse viewCourseDetail(long courseID) throws JsonProcessingException {

        Course course = courseRepository.findByIdAndIsActiveTrue(courseID)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay course") + courseID));

        return convertCourseToCourseDetailResponse(course);
    }

    @Override
    public CourseDetailResponse updateCourse(long courseID, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay course" + courseID));
        course.setCode(courseRequest.getCode());
        course.setName(courseRequest.getName());
        course.setGrade(courseRequest.getGradeType());
        Subject subject = subjectRepository.findById(courseRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay subject") + courseRequest.getSubjectId()));
        course.setSubject(subject);

        List<TeacherCourse> teacherCourseList = new ArrayList<>();
        List<Long> teacherIds = courseRequest.getTeacherIds();
        course.getTeacherCourses().clear();


        for (Long teacherId : teacherIds) {
            TeacherCourse teacherCourse = new TeacherCourse();
            Account teacher = accountRepository.findById(teacherId)
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay teacher") + teacherId));
            if (teacher.getRole().getCode().name().equals(EAccountRole.TEACHER.name())) {
                TeacherCourseKey teacherCourseKey = new TeacherCourseKey();
                teacherCourseKey.setCourseId(course.getId());
                teacherCourseKey.setTeachId(teacherId);
                teacherCourse.setId(teacherCourseKey);
                teacherCourse.setCourse(course);
                teacherCourse.setAccount(teacher);
                teacherCourseList.add(teacherCourse);
            }
        }

        course.getTeacherCourses().addAll(teacherCourseList);
        Course save = courseRepository.save(course);
        CourseDetailResponse courseDetailResponse = ObjectUtil.copyProperties(save, new CourseDetailResponse(), CourseDetailResponse.class);

        courseDetailResponse.setSubject(ObjectUtil.copyProperties(subject, new SubjectDto(), SubjectDto.class));

        List<TeacherCourseDto> teacherCourseDtoList = new ArrayList<>();
        teacherCourseList.stream().map(teacherCourse -> {
            TeacherCourseDto teacherCourseDto = new TeacherCourseDto();
            teacherCourseDto.setTeacherId(teacherCourse.getAccount().getId());
            teacherCourseDto.setTopicId(teacherCourse.getCourse().getId());

            teacherCourseDto.setIsAllowed(teacherCourse.getIsAllowed());

            teacherCourseDtoList.add(teacherCourseDto);
            return teacherCourseDto;
        }).collect(Collectors.toList());
        courseDetailResponse.setTeacherCourse(teacherCourseDtoList);
        return courseDetailResponse;
    }

    @Override
    public List<ClassCourseResponse> viewHistoryCourse(long studentId) {
        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("teacher not found with id:" + studentId));
        List<StudentClass> studentClasses = account.getStudentClasses();
        List<ClassCourseResponse> classCourseResponseList = new ArrayList<>();

        ClassCourseResponse courseResponse = new ClassCourseResponse();
        courseResponse.setStudentId(account.getId());
//        if (studentClasses != null) {
        for (StudentClass studentClass : studentClasses) {
            courseResponse.setStudentId(account.getId());
            courseResponse.setClazz(ObjectUtil.copyProperties(studentClass.getaClass(), new ClassDto(), ClassDto.class));
            Class aClass = studentClass.getaClass();
            if (aClass != null) {
                Course course = aClass.getCourse();
                courseResponse.setCourse(ObjectUtil.copyProperties(course, new CourseDto(), CourseDto.class));
                CourseDto courseDto = new CourseDto() ;
                courseDto.setUnitPrice(course.getUnitPrice());
                courseDto.setFinalPrice(course.getFinalPrice());
                if (course != null) {
                    Subject subject = course.getSubject();
                    if (subject != null) {
                        courseResponse.setSubject(ObjectUtil.copyProperties(subject, new SubjectDto(), SubjectDto.class));
                    }
                }
            }

        }
        classCourseResponseList.add(courseResponse);

//        }
        return classCourseResponseList;
    }


    @Override
    public ClassCourseResponse studentEnrollCourse(long studentId, long courseId, long classId) {

        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("student not found with id:" + studentId));

//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay course" + courseId));

//        Class aClass = classRepository.findByIdAndCourse(classId, course).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay course" + courseId));

        Class aClass = classRepository.findById(classId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));

        ClassCourseResponse classCourseResponse = null ;

        if (aClass.getMaxNumberStudent() < aClass.getNumberStudent()){

            classCourseResponse  = new ClassCourseResponse() ;
            StudentClass studentClass = new StudentClass() ;
            StudentClassKey studentClassKey = new StudentClassKey() ;
            studentClassKey.setClassId(aClass.getId());
            studentClassKey.setStudentId(account.getId());
            studentClass.setId(studentClassKey);
            studentClass.setAccount(account);
            studentClass.setAClass(aClass);
            studentClass.setEnrollDate(Instant.now());

            aClass.getStudentClasses().add(studentClass);
            aClass.setNumberStudent(aClass.getNumberStudent()+1 );
            classRepository.save(aClass) ;


            // response
            classCourseResponse.setStudentId(studentId);
            classCourseResponse.setClazz(ObjectUtil.copyProperties(studentClass.getaClass(), new ClassDto(), ClassDto.class));
            Class aClass1 = studentClass.getaClass();
            if (aClass1 != null) {
                Course course1 = aClass.getCourse();
                classCourseResponse.setCourse(ObjectUtil.copyProperties(course1, new CourseDto(), CourseDto.class));
                CourseDto courseDto = new CourseDto() ;
                courseDto.setUnitPrice(course1.getUnitPrice());
                courseDto.setFinalPrice(course1.getFinalPrice());
                if (course1 != null) {
                    Subject subject = course1.getSubject();
                    if (subject != null) {
                        classCourseResponse.setSubject(ObjectUtil.copyProperties(subject, new SubjectDto(), SubjectDto.class));
                    }
                }
            }

        }
        //response

        return classCourseResponse;
    }

    @Override
    public List<MoodleRecourseClassResponse> synchronizedResource(Long classId) throws JsonProcessingException {
        CourseIdRequest courseIdRequest = new CourseIdRequest();
        courseIdRequest.setCourseid(classId );
        MoodleMasterDataRequest s1MasterDataRequest = new MoodleMasterDataRequest() ;
        List<MoodleRecourseClassResponse> resourceCourse = moodleCourseRepository.getResourceCourse(courseIdRequest);
        System.out.println(resourceCourse);
        return resourceCourse;
    }

    @Override
    public Boolean createCourse(CourseRequest courseRequest) {
        Course course = new Course();
        course.setName(courseRequest.getName());
        if (courseRepository.existsByCode(courseRequest.getCode())){
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("course code da ton tai"));
        }
        course.setCode(courseRequest.getCode());
        course.setGrade(courseRequest.getGradeType());
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setIsActive(false);
        courseRepository.save(course);
        return true;
    }

//
//    @Override
//    public List<CourseDetailResponse> getAll() {
//        return null;
//    }
//
//    @Override
//    public List<CourseDetailResponse> searchCourseByName(String name) {
//        return null;
//    }
//
//    @Override
//    public CourseDetailResponse create(CourseRequest courseRequest) {
//
//        Grade grade = gradeRepository.findById(courseRequest.getGradeId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Grade not found with id:" + courseRequest.getGradeId()));
//        Subject subject = subjectRepository.findById(courseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + courseRequest.getSubjectId()));
////        List<Teacher> teachers = teacherRepository.findAllById(courseRequest.getTeacherIds());
////        if (teachers.size() < courseRequest.getTeacherIds().size()) {
////            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Teacher ids are invalid!");
////        }
//
//        Course course = ObjectUtil.copyProperties(courseRequest, new Course(), Course.class, true);
//        course.setGrade(grade);
//        course.setSubject(subject);
////        course.setTeacherCourses(teachers.stream().map(teacher -> {
////            TeacherCourse teacherCourse = new TeacherCourse();
////            teacherCourse.setCourse(course);
////            teacherCourse.setTeacher(teacher);
////            return teacherCourse;
////        }).collect(Collectors.toList()));
//        courseRepository.save(course);
//
//        return convertCourseToCourseResponse(course);
//
//        return null;
//
//    }
//
//    @Override
//    public CourseDetailResponse update(CourseRequest courseRequest, Long id) {
//
//        Course course = courseRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Course not found by id:" + id));
//        Grade grade = gradeRepository.findById(courseRequest.getGradeId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Grade not found with id:" + courseRequest.getGradeId()));
//        Subject subject = subjectRepository.findById(courseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + courseRequest.getSubjectId()));
////        List<Teacher> teachers = teacherRepository.findAllById(courseRequest.getTeacherIds());
////        if (teachers.size() < courseRequest.getTeacherIds().size()) {
////            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Teacher ids are invalid!");
////        }
//
//        Course newCourse = ObjectUtil.copyProperties(courseRequest, new Course(), Course.class, true);
//        course = ObjectUtil.copyProperties(newCourse, course, Course.class, true);
//
//        course.setGrade(grade);
//        course.setSubject(subject);
//        Course finalCourse = course;
////        course.setTeacherCourses(teachers.stream().map(teacher -> {
////            TeacherCourse teacherCourse = new TeacherCourse();
////            teacherCourse.setCourse(finalCourse);
////            teacherCourse.setTeacher(teacher);
////            return teacherCourse;
////        }).collect(Collectors.toList()));
//        return convertCourseToCourseResponse(course);
//
//        return null;
//
//    }
//
//    @Override
//    public Boolean delete(Long id) {
//        return null;
//    }
////    private final CourseRepository courseRepository;
////
////    private final SubjectRepository subjectRepository;
////
////    private final TeacherCourseRepository teacherCourseRepository;
////
////    public CourseServiceImpl(CourseRepository courseRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, TeacherCourseRepository teacherCourseRepository) {
////        this.courseRepository = courseRepository;
////        this.gradeRepository = gradeRepository;
////        this.subjectRepository = subjectRepository;
////        this.teacherRepository = teacherRepository;
////        this.teacherCourseRepository = teacherCourseRepository;
////    }
////
////    @Override
////    public List<CourseDetailResponse> getAll() {
////        List<Course> courses = courseRepository.findAll();
////        return courses.stream().map(this::convertCourseToCourseResponse).collect(Collectors.toList());
////    }
////
////    @Override
////    public List<CourseDetailResponse> searchCourseByName(String name) {
////        List<Course> courses = courseRepository.searchCourseByNameContainsIgnoreCase(name.trim());
////        return courses.stream().map(course -> convertCourseToCourseResponse(course)).collect(Collectors.toList());
////    }
////
////    @Override
////    public CourseDetailResponse create(CourseRequest courseRequest) {
////        Grade grade = gradeRepository.findById(courseRequest.getGradeId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Grade not found with id:" + courseRequest.getGradeId()));
////        Subject subject = subjectRepository.findById(courseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + courseRequest.getSubjectId()));
////        List<Teacher> teachers = teacherRepository.findAllById(courseRequest.getTeacherIds());
////        if (teachers.size() < courseRequest.getTeacherIds().size()) {
////            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Teacher ids are invalid!");
////        }
////
////        Course course = ObjectUtil.copyProperties(courseRequest, new Course(), Course.class, true);
////        course.setGrade(grade);
////        course.setSubject(subject);
//////        course.setTeacherCourses(teachers.stream().map(teacher -> {
//////            TeacherCourse teacherCourse = new TeacherCourse();
//////            teacherCourse.setCourse(course);
//////            teacherCourse.setTeacher(teacher);
//////            return teacherCourse;
//////        }).collect(Collectors.toList()));
////        courseRepository.save(course);
////
////        return convertCourseToCourseResponse(course);
////    }
////
////    @Override
////    public CourseDetailResponse update(CourseRequest courseRequest, Long id) {
////        Course course = courseRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Course not found by id:" + id));
////        Grade grade = gradeRepository.findById(courseRequest.getGradeId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Grade not found with id:" + courseRequest.getGradeId()));
////        Subject subject = subjectRepository.findById(courseRequest.getSubjectId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + courseRequest.getSubjectId()));
////        List<Teacher> teachers = teacherRepository.findAllById(courseRequest.getTeacherIds());
////        if (teachers.size() < courseRequest.getTeacherIds().size()) {
////            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Teacher ids are invalid!");
////        }
////
////        Course newCourse = ObjectUtil.copyProperties(courseRequest, new Course(), Course.class, true);
////        course = ObjectUtil.copyProperties(newCourse, course, Course.class, true);
////
////        course.setGrade(grade);
////        course.setSubject(subject);
////        Course finalCourse = course;
//////        course.setTeacherCourses(teachers.stream().map(teacher -> {
//////            TeacherCourse teacherCourse = new TeacherCourse();
//////            teacherCourse.setCourse(finalCourse);
//////            teacherCourse.setTeacher(teacher);
//////            return teacherCourse;
//////        }).collect(Collectors.toList()));
////        return convertCourseToCourseResponse(course);
////    }
////
////    @Override
////    public Boolean delete(Long id) {
////        Course course = courseRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Course not found by id:" + id));
////        courseRepository.delete(course);
////        return true;
////    }
////
////    private CourseDetailResponse convertCourseToCourseResponse(Course course) {
////        CourseDetailResponse courseResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class, true);
////        GradeResponse gradeResponse = ObjectUtil.copyProperties(course.getGrade(), new GradeResponse(), GradeResponse.class, true);
////        SubjectResponse subjectResponse = ObjectUtil.copyProperties(course.getSubject(), new SubjectResponse(), SubjectResponse.class, true);
////        courseResponse.setGrade(gradeResponse);
////        courseResponse.setSubject(subjectResponse);
////        return courseResponse;
////    }
}
