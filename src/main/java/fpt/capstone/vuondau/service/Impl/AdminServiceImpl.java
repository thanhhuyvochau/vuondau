package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;


import fpt.capstone.vuondau.entity.dto.*;

import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.ConvertUtil;
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
    public AccountResponse updateRoleAccount(long id, EAccountRole eAccountRole) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
        Role role = roleRepository.findRoleByCode(eAccountRole)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay role"));
        account.setRole(role);
        Account save = accountRepository.save(account);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(role, new RoleDto(), RoleDto.class));
        return accountResponse;
    }

    @Override
    public ApiPage<CourseDetailResponse> searchCourse(CourseSearchRequest query, Pageable pageable) {
        CourseSpecificationBuilder builder = CourseSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Course> coursePage = courseRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(coursePage.map(this::convertCourseToCourseResponse));

    }

    public CourseDetailResponse convertCourseToCourseResponse(Course course) {
        CourseDetailResponse courseDetailResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class);
        courseDetailResponse.setGrade(course.getGrade());
        return courseDetailResponse;
    }

    @Override
    public AccountResponse ApproveAccountTeacher(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay account") + id));
        account.setActive(true);
        Account save = accountRepository.save(account);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
        return accountResponse;
    }

    @Override
    public FeedBackDto viewStudentFeedbackClass(Long classId) {
        Class fbclass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay class") + classId));


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
    public ApiPage<RequestFormResponese> searchRequestForm(RequestSearchRequest query, Pageable pageable) {
        RequestFormSpecificationBuilder builder = RequestFormSpecificationBuilder.specification()
                .queryLike(query.getQ());
        Page<Request> requestPage = requestRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(requestPage.map(ConvertUtil::doConvertEntityToResponse));
    }


}
