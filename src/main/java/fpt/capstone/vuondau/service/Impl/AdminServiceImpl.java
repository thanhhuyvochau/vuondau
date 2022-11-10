package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Dto.*;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;

import fpt.capstone.vuondau.util.specification.CourseSpecificationBuilder;
import fpt.capstone.vuondau.util.specification.RequestFormSpecificationBuilder;
import fpt.capstone.vuondau.util.specification.SubjectSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final RoleRepository roleRepository;

    private final CourseRepository courseRepository;

    private final ClassRepository classRepository;

    private final FeedbackRepository feedbackRepository;


    private final SubjectRepository subjectRepository;

    private final TeacherCourseRepository teacherCourseRepository;

    private final RequestRepository requestRepository;

    public AdminServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, RoleRepository roleRepository, CourseRepository courseRepository, ClassRepository classRepository, FeedbackRepository feedbackRepository, SubjectRepository subjectRepository, TeacherCourseRepository teacherCourseRepository, RequestRepository requestRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;

        this.classRepository = classRepository;
        this.feedbackRepository = feedbackRepository;
        this.subjectRepository = subjectRepository;
        this.teacherCourseRepository = teacherCourseRepository;

        this.requestRepository = requestRepository;
    }

    @Override
    public ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable) {
        AccountSpecificationBuilder builder = AccountSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Account> accountPage = accountRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(accountPage.map(this::convertAccountToAccountResponse));

    }


    public AccountResponse convertAccountToAccountResponse(Account account) {
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
        if (account.getRole() != null) {
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
        }
        return accountResponse;
    }


    @Override
    public ApiPage<AccountResponse> viewAllAccountDetail(Pageable pageable) {
        Page<Account> allAccount = accountRepository.findAll(pageable);
        return PageUtil.convert(allAccount
                .map(account -> {
                    AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
                    if (account.getRole() != null) {
                        accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
                    }
                    return accountResponse;
                }));
    }

    @Override
    public AccountResponse viewAccountDetail(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
        if (account.getRole() != null) {
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
        }
        return accountResponse;
    }

    @Override
    public Boolean banAndUbBanAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        if (account.getActive() == true) {
            account.setActive(false);
        } else {
            account.setActive(true);
        }
        accountRepository.save(account);
        return true;
    }

    @Override
    public AccountResponse updateRoleAccount(long id, EAccountRole eAccountRole) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        Role role = roleRepository.findRoleByCode(eAccountRole.name())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);
        Account save = accountRepository.save(account);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(role, new RoleDto(), RoleDto.class));
        return accountResponse;
    }

    @Override
    public ApiPage<CourseResponse> searchCourse(CourseSearchRequest query, Pageable pageable) {
        CourseSpecificationBuilder builder = CourseSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Course> coursePage = courseRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(coursePage.map(this::convertCourseToCourseResponse));

    }

    public CourseResponse convertCourseToCourseResponse(Course course) {
        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
        courseResponse.setGrade(course.getGrade());
        return courseResponse;
    }

    @Override
    public AccountResponse ApproveAccountTeacher(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        account.setActive(true);
        Account save = accountRepository.save(account);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
        return accountResponse;
    }

    @Override
    public FeedBackDto viewStudentFeedbackClass(Long classId) {
        Class fbclass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay class") + classId));


        FeedBackDto feedBackDto = new FeedBackDto();
        ClassDto classDto = ObjectUtil.copyProperties(fbclass, new ClassDto(), ClassDto.class);
        feedBackDto.setClassInfo(classDto);

        List<FeedBack> feedBacks = feedbackRepository.countAllByClazz(fbclass);
        List<FeedBacClassDto> feedBacClassDtoList = new ArrayList<>();
        feedBacks.stream().map(feedBack -> {
            FeedBacClassDto feedBacClassDto = ObjectUtil.copyProperties(feedBack, new FeedBacClassDto(), FeedBacClassDto.class);
            feedBacClassDtoList.add(feedBacClassDto);
            return feedBacClassDto;
        }).collect(Collectors.toList());

        feedBackDto.setFeedBacClass(feedBacClassDtoList);
        return feedBackDto;
    }

    @Override
    public ApiPage<SubjectResponse> searchSubject(SubjectSearchRequest query, Pageable pageable) {
        SubjectSpecificationBuilder builder = SubjectSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Subject> coursePage = subjectRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(coursePage.map(this::convertSubjectToSubjectResponse));

    }

    public SubjectResponse convertSubjectToSubjectResponse(Subject subject) {
        SubjectResponse courseResponse = ObjectUtil.copyProperties(subject, new SubjectResponse(), SubjectResponse.class);
        return courseResponse;
    }


    @Override
    public ApiPage<SubjectResponse> viewAllSubject(Pageable pageable) {
        Page<Subject> allSubjects = subjectRepository.findAll(pageable);
        return PageUtil.convert(allSubjects
                .map(subject -> {
                    SubjectResponse subjectResponse = ObjectUtil.copyProperties(subject, new SubjectResponse(), SubjectResponse.class);

                    return subjectResponse;
                }));

    }

    @Override
    public SubjectResponse viewSubjectDetail(long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + subjectId));
        SubjectResponse subjectResponse = ObjectUtil.copyProperties(subject, new SubjectResponse(), SubjectResponse.class);
        return subjectResponse;
    }

    @Override
    public Boolean deleteSubject(long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + subjectId));
        subjectRepository.delete(subject);
        return true;
    }

    @Override
    public SubjectResponse updateSubject(long subjectId, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + subjectId));
        subject.setCode(subjectRequest.getCode());
        subject.setName(subject.getName());
        List<Course> courses = courseRepository.findAllById(subjectRequest.getCourseIds());
        if (!courses.isEmpty()) {
            subject.setCourses(courses);
        }
        Subject save = subjectRepository.save(subject);
        return ObjectUtil.copyProperties(save, new SubjectResponse(), SubjectResponse.class);

    }

    @Override
    public ApiPage<CourseResponse> viewAllCourse(Pageable pageable) {
        Page<Course> allCourse = courseRepository.findAll(pageable);
        return PageUtil.convert(allCourse
                .map(course -> {
                    CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
                    return courseResponse;
                }));
    }

    @Override
    public CourseResponse viewCourseDetail(long courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay course") + courseID));
        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
        return courseResponse;
    }

    @Override
    public CourseResponse updateCourse(long courseID, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay course") + courseID));
        course.setCode(courseRequest.getCode());
        course.setName(courseRequest.getName());
        course.setGrade(courseRequest.getGradeType());
        Subject subject = subjectRepository.findById(courseRequest.getSubjectId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay subject") + courseRequest.getSubjectId()));
        course.setSubject(subject);


        List<TeacherCourse> teacherCourseList = new ArrayList<>();

        for (Long id : courseRequest.getTeacherIds()) {

            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay teacher") + id));
            TeacherCourse teacherCourse1 = new TeacherCourse();


            teacherCourse1.setCourse(course);
            teacherCourse1.setAccount(account);
            teacherCourseList.add(teacherCourse1);


        }
        course.getTeacherCourses().clear();
        course.getTeacherCourses().addAll(teacherCourseList);


        Course save = courseRepository.save(course);
        return ObjectUtil.copyProperties(save, new CourseResponse(), CourseResponse.class);

    }

    @Override
    public ApiPage<RequestFormResponese> searchRequestForm(RequestSearchRequest query, Pageable pageable) {
        RequestFormSpecificationBuilder builder = RequestFormSpecificationBuilder.specification()
                .queryLike(query.getQ());
        Page<Request> requestPage = requestRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(requestPage.map(this::convertRequestToRequestResponse));
    }
    public RequestFormResponese convertRequestToRequestResponse(Request request) {
        RequestFormResponese requestFormResponese = ObjectUtil.copyProperties(request, new RequestFormResponese(), RequestFormResponese.class);
        requestFormResponese.setRequestType(ObjectUtil.copyProperties(request.getRequestType(), new RequestTypeDto(), RequestTypeDto.class));
        return requestFormResponese;
    }


}
