package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ICourseService;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import fpt.capstone.vuondau.util.specification.CourseSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherCourseRepository teacherCourseRepository;

    private final AccountRepository accountRepository;

    private final ClassRepository classRepository;


    public CourseServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, TeacherCourseRepository teacherCourseRepository, AccountRepository accountRepository, ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.teacherCourseRepository = teacherCourseRepository;
        this.accountRepository = accountRepository;
        this.classRepository = classRepository;
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

//    public CourseResponse convertCourseToCourseResponse(Course course) {
//        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
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


        // set course
        courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class, true);

        // set course detail


        // set subject
        Subject subject = course.getSubject();
        if (subject != null) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setId(subject.getId());
            subjectDto.setName(subject.getName());
            subjectDto.setCode(subject.getCode());
            courseResponse.setSubject(subjectDto);
        }
        // set Class
        Account account = course.getTeacherCourses().stream().map(TeacherCourse::getAccount).findFirst().get();
        Class classes = classRepository.findByCourseAndAccount(course, account);
        if (classes!= null) {
            ClassDto classDto = new ClassDto();
            classDto.setName(classes.getName());
            classDto.setCode(classes.getCode());
            classDto.setLevel(classes.getLevel());
            classDto.setStartDate(classes.getStartDate());
            classDto.setEndDate(classes.getEndDate());
            classDto.setNumberStudent(classes.getNumberStudent());
            classDto.setMaxNumberStudent(classes.getMaxNumberStudent());

            courseResponse.setClazz(classDto);
        }

        // set teacher course
        List<TeacherCourseDto> teacherCourseDtoList = new ArrayList<>();
        List<TeacherCourse> teacherCourses = course.getTeacherCourses();
        teacherCourses.stream().map(teacherCourse -> {
            TeacherCourseDto teacherCourseDto = new TeacherCourseDto();
            teacherCourseDto.setTopicId(teacherCourse.getCourse().getId());
            teacherCourseDto.setIsAllowed(teacherCourse.getIsAllowed());
            teacherCourseDto.setTeacherId(teacherCourse.getAccount().getId());
            teacherCourseDto.setTeacherName(teacherCourse.getAccount().getFirstName() +" "+ teacherCourse.getAccount().getLastName());
            teacherCourseDtoList.add(teacherCourseDto);

            return teacherCourse;
        }).collect(Collectors.toList());

        courseResponse.setTeacherCourse(teacherCourseDtoList);
        return courseResponse;
    }

    @Override
    public CourseResponse viewCourseDetail(long courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay course") + courseID));
        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
        return courseResponse;
    }

    @Override
    public CourseResponse updateCourse(long courseID, CourseRequest courseRequest) {
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
        CourseResponse courseResponse = ObjectUtil.copyProperties(save, new CourseResponse(), CourseResponse.class);

        courseResponse.setSubject(ObjectUtil.copyProperties(subject, new SubjectDto(), SubjectDto.class));

        List<TeacherCourseDto> teacherCourseDtoList = new ArrayList<>();
        teacherCourseList.stream().map(teacherCourse -> {
            TeacherCourseDto teacherCourseDto = new TeacherCourseDto();
            teacherCourseDto.setTeacherId(teacherCourse.getAccount().getId());
            teacherCourseDto.setTopicId(teacherCourse.getCourse().getId());

            teacherCourseDto.setIsAllowed(teacherCourse.getIsAllowed());

            teacherCourseDtoList.add(teacherCourseDto);
            return teacherCourseDto;
        }).collect(Collectors.toList());
        courseResponse.setTeacherCourse(teacherCourseDtoList);
        return courseResponse;
    }

//
//    @Override
//    public List<CourseResponse> getAll() {
//        return null;
//    }
//
//    @Override
//    public List<CourseResponse> searchCourseByName(String name) {
//        return null;
//    }
//
//    @Override
//    public CourseResponse create(CourseRequest courseRequest) {
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
//    public CourseResponse update(CourseRequest courseRequest, Long id) {
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
////    public List<CourseResponse> getAll() {
////        List<Course> courses = courseRepository.findAll();
////        return courses.stream().map(this::convertCourseToCourseResponse).collect(Collectors.toList());
////    }
////
////    @Override
////    public List<CourseResponse> searchCourseByName(String name) {
////        List<Course> courses = courseRepository.searchCourseByNameContainsIgnoreCase(name.trim());
////        return courses.stream().map(course -> convertCourseToCourseResponse(course)).collect(Collectors.toList());
////    }
////
////    @Override
////    public CourseResponse create(CourseRequest courseRequest) {
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
////    public CourseResponse update(CourseRequest courseRequest, Long id) {
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
////    private CourseResponse convertCourseToCourseResponse(Course course) {
////        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class, true);
////        GradeResponse gradeResponse = ObjectUtil.copyProperties(course.getGrade(), new GradeResponse(), GradeResponse.class, true);
////        SubjectResponse subjectResponse = ObjectUtil.copyProperties(course.getSubject(), new SubjectResponse(), SubjectResponse.class, true);
////        courseResponse.setGrade(gradeResponse);
////        courseResponse.setSubject(subjectResponse);
////        return courseResponse;
////    }
}
