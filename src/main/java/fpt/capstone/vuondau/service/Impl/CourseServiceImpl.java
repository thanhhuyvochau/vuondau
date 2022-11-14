package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.CourseDto;
import fpt.capstone.vuondau.entity.request.TopicsSubjectRequest;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ICourseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherCourseRepository teacherCourseRepository;

    private final AccountRepository accountRepository ;

    public CourseServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, TeacherCourseRepository teacherCourseRepository, AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.teacherCourseRepository = teacherCourseRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TopicsSubjectRequest teacherCreateTopicInSubject( Long teacherId  , TopicsSubjectRequest topicsSubjectRequest) {

        Subject subject = subjectRepository.findById(topicsSubjectRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("subject not found with id:" + topicsSubjectRequest.getSubjectId()));
        Account account = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("teacher not found with id:" + teacherId));
        List<Course>courseList = new ArrayList<>() ;

        List<CourseDto> coursesRequest = topicsSubjectRequest.getCourses();
        for (CourseDto courseDto : coursesRequest) {
            Course course = new Course() ;
            course.setCode(courseDto.getCode());
            course.setName(courseDto.getName());
            course.setGrade(courseDto.getGrade());
            course.setSubject(subject);
            List<TeacherCourse> teacherCourseList = new ArrayList<>() ;
            TeacherCourse teacherCourse = new TeacherCourse() ;
            TeacherCourseKey teacherCourseKey= new TeacherCourseKey() ;
            teacherCourseKey.setTeachId(account.getId());
            teacherCourseKey.setCourseId(course.getId());
            teacherCourse.setId(teacherCourseKey);
            teacherCourse.setCourse(course);
            teacherCourse.setAccount(account);
            teacherCourseList.add(teacherCourse) ;
            course.setTeacherCourses(teacherCourseList);
            courseList.add(course) ;
        }
        courseRepository.saveAll(courseList) ;
        return null;
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
