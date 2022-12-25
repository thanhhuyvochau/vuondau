package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.entity.dto.SimpleForumDto;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.ForumRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IForumService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumServiceImpl implements IForumService {


    private final ForumRepository forumRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final SecurityUtil securityUtil;

    public ForumServiceImpl(ForumRepository forumRepository, ClassRepository classRepository, SubjectRepository subjectRepository, SecurityUtil securityUtil) {
        this.forumRepository = forumRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public ForumDto createForumForClass(Long classId, MoodleSectionResponse moodleSectionResponse) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Class not found with id:" + classId));
        Forum existedForum = forumRepository.findForumByClazz(clazz).orElse(null);
        if (existedForum != null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("A class at most one forum");
        }

        Forum forum = new Forum();
        forum.setName(clazz.getName());
        forum.setCode(clazz.getCode());
        forum.setType(EForumType.CLASS);

        List<ForumLesson> forumLessons = moodleSectionResponse.getModules().stream().filter(resourceMoodleResponse -> resourceMoodleResponse.getModname().equals("lesson")).map(resourceMoodleResponse -> {
            ForumLesson forumLesson = new ForumLesson();
            forumLesson.setForum(forum);
            forumLesson.setLessonName(resourceMoodleResponse.getName());
            return forumLesson;
        }).collect(Collectors.toList());

        forum.setForumLessons(forumLessons);
        Forum savedForum = forumRepository.save(forum);
        return ConvertUtil.doConvertEntityToResponse(savedForum);
    }

    @Override
    public ForumDto createForumForSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + subjectId));

        Forum existedForum = forumRepository.findForumBySubject(subject).orElse(null);
        if (existedForum != null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("A class at most one forum");
        }

        Forum forum = new Forum();
        forum.setName(subject.getName());
        forum.setCode(subject.getCode().name());
        forum.setType(EForumType.SUBJECT);
        forumRepository.save(forum);
        return ConvertUtil.doConvertEntityToResponse(forum);
    }

    @Override
    public ForumDto updateForum(Long id) {
        return null;
    }

    @Override
    public ForumDto getForumByClass(Long classId) {
        Account account = securityUtil.getCurrentUser();
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Class not found with id:" + classId));

        if (!isValidClassForStudent(account, clazz)) {
            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Student not enrolled to this class or some error happened!!");
        }
        Forum forum = forumRepository.findForumByClazz(clazz)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Forum not found with class:" + clazz.getName()));

        return ConvertUtil.doConvertEntityToResponse(forum);
    }

    @Override
    public ForumDto getForumBySubject(Long subjectId) {
        Account account = securityUtil.getCurrentUser();
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Subject not found with id:" + subjectId));
        Forum forum = subject.getForum();
        if (forum == null) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Forum of this subject not found, contact admin for help!");
        }

        String roleCode = account.getRole().getCode().name();
        switch (roleCode) {
            case "ADMIN":
                return ConvertUtil.doConvertEntityToResponse(subject.getForum());
            case "TEACHER":
                if (!isValidSubjectForTeacher(account, subject)) {
                    throw ApiException.create(HttpStatus.CONFLICT).withMessage("This forum subject invalid for you!");
                }
                return ConvertUtil.doConvertEntityToResponse(subject.getForum());
            case "STUDENT":
                if (!isValidSubjectForStudent(account, subject)) {
                    throw ApiException.create(HttpStatus.CONFLICT).withMessage("This forum subject invalid for you!");
                }
                return ConvertUtil.doConvertEntityToResponse(subject.getForum());
        }
        return null;
    }


    private Boolean isValidSubjectForTeacher(Account account, Subject subject) {
        AccountDetail teacherAccountDetail = account.getAccountDetail();
        if (teacherAccountDetail == null) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Cannot find the profile of this teacher, contact admin for help!");
        }
        AccountDetail result = subject.getAccountDetailSubjects()
                .stream()
                .map(AccountDetailSubject::getAccountDetail)
                .filter(accountDetail -> accountDetail.getId().equals(teacherAccountDetail.getId()))
                .findFirst().orElse(null);
        if (result == null) {
            return false;
        }
        return true;
    }

    @Override
    public ApiPage<SimpleForumDto> getAllForumByTypes(Pageable pageable, EForumType forumType) {
        if (forumType.name().equals(EForumType.CLASS.name())) {
            return getAllClassForums(pageable);
        } else if (forumType.name().equals(EForumType.SUBJECT.name())) {
            return getAllSubjectForums(pageable);
        }
        return null;
    }


    private ApiPage<SimpleForumDto> getAllSubjectForums(Pageable pageable) {
        Account account = securityUtil.getCurrentUser();
        String roleCode = account.getRole().getCode().name();
        switch (roleCode) {
            case "STUDENT":
                return getSubjectForumsOfStudent(account, pageable);
            case "TEACHER":
                return getSubjectForumsOfTeacher(account, pageable);
            case "ADMIN":
                return getForumsForAdmin(EForumType.SUBJECT, pageable);
            default:
                throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage("Invalid user to call this method!");
        }
    }


    private ApiPage<SimpleForumDto> getSubjectForumsOfStudent(Account account, Pageable pageable) {
        List<Subject> enrolledSubjects = account.getStudentClasses().stream()
                .map(StudentClass::getaClass)
                .map(Class::getCourse)
                .map(Course::getSubject)
                .collect(Collectors.toList());
        Page<Forum> forums = forumRepository.findAllBySubjectIn(enrolledSubjects, pageable);
        return PageUtil.convert(forums.map(ConvertUtil::doConvertEntityToSimpleResponse));
    }

    private ApiPage<SimpleForumDto> getSubjectForumsOfTeacher(Account account, Pageable pageable) {
        AccountDetail accountDetail = account.getAccountDetail();
        if (accountDetail != null) {
            List<Subject> taughtSubject = accountDetail.getAccountDetailSubjects().stream()
                    .map(AccountDetailSubject::getSubject)
                    .collect(Collectors.toList());

            Page<Forum> forums = forumRepository.findAllBySubjectIn(taughtSubject, pageable);
            return PageUtil.convert(forums.map(ConvertUtil::doConvertEntityToSimpleResponse));
        }
        return PageUtil.convert(Page.empty());
    }

    private ApiPage<SimpleForumDto> getAllClassForums(Pageable pageable) {
        Account account = securityUtil.getCurrentUser();
        String roleCode = account.getRole().getCode().name();
        switch (roleCode) {
            case "STUDENT":
                return getClassesForumsOfStudent(account, pageable);
            case "TEACHER":
                return getClassesForumsOfTeacher(account, pageable);
            case "ADMIN":
                return getForumsForAdmin(EForumType.CLASS, pageable);
            default:
                throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage("Invalid user to call this method!");
        }
    }

    private ApiPage<SimpleForumDto> getClassesForumsOfStudent(Account student, Pageable pageable) {
        List<Class> enrolledClasses = student.getStudentClasses().stream()
                .map(StudentClass::getaClass).collect(Collectors.toList());
        Page<Forum> classForums = forumRepository.findAllByClazzIn(enrolledClasses, pageable);
        return PageUtil.convert(classForums.map(ConvertUtil::doConvertEntityToSimpleResponse));
    }

    private ApiPage<SimpleForumDto> getClassesForumsOfTeacher(Account account, Pageable pageable) {
        List<Class> teachedClass = account.getTeacherClass();
        Page<Forum> classForums = forumRepository.findAllByClazzIn(teachedClass, pageable);
        return PageUtil.convert(classForums.map(ConvertUtil::doConvertEntityToSimpleResponse));
    }

    private ApiPage<SimpleForumDto> getForumsForAdmin(EForumType forumType, Pageable pageable) {
        Page<Forum> forums = forumRepository.findAllByType(forumType, pageable);
        return PageUtil.convert(forums.map(ConvertUtil::doConvertEntityToSimpleResponse));
    }

    private Boolean isValidSubjectForStudent(Account student, Subject subject) {
        List<Class> enrolledClass = student.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());
        Class classMatchSubject = enrolledClass.stream()
                .filter(aClass -> aClass.getCourse().getSubject() != null)
                .filter(aClass -> aClass.getCourse().getSubject().getId().equals(subject.getId()))
                .findFirst().orElse(null);
        if (classMatchSubject == null) {
            return false;
        }
        return true;
    }

    private Boolean isValidClassForStudent(Account student, Class clazz) {
        long enrolled = clazz.getStudentClasses()
                .stream()
                .filter(studentClass -> studentClass.getAccount().getId().equals(student.getId())).count();
        if (enrolled != 1) {
            return false;
        }
        return true;
    }
}
