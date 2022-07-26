package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.ForumRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.IForumService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
        forum.setName(clazz.getName() + " Forum");
        forum.setCode(clazz.getCode() + "FO");
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
        forum.setName(subject.getName() + " Forum");
        forum.setCode(subject.getCode() + "FO");
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

        if (!isEnrolledToClass(account, clazz)) {
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
        Forum forum = null;
        boolean isValidToViewQuestion = true;
        if (account.getRole().getCode().name().equals(EAccountRole.STUDENT.name())) {
            isValidToViewQuestion = isEnrolledToClassBelongToSubject(account, subject);
        }
        if (isValidToViewQuestion) {
            forum = forumRepository.findForumBySubject(subject)
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                            .withMessage("Forum not found with class:" + subject.getName()));
        }
        return ConvertUtil.doConvertEntityToResponse(forum);
    }

    @Override
    public ApiPage<ForumDto> getAllSubjectForums(Pageable pageable) {
        Account account = securityUtil.getCurrentUser();
        ApiPage<ForumDto> subjectForums = null;
        if (account.getRole().getCode().equals(EAccountRole.STUDENT)) {
            subjectForums = getAllSubjectForumsOfStudent(account, pageable);
        } else if (account.getRole().getCode().equals(EAccountRole.TEACHER)
                || account.getRole().getCode().equals(EAccountRole.ADMIN)) {
            Page<Forum> subjectForumsList = forumRepository.findAllByType(EForumType.SUBJECT, pageable);
            subjectForums = PageUtil.convert(subjectForumsList.map(ConvertUtil::doConvertEntityToResponse));
        }
        return subjectForums;
    }


    private ApiPage<ForumDto> getAllSubjectForumsOfStudent(Account account, Pageable pageable) {
        List<ForumDto> forumClass = account.getStudentClasses().stream()
                .map(StudentClass::getaClass).map(Class::getCourse).map(Course::getSubject).distinct()
                .map(subject -> this.getForumBySubject(subject.getId()))
                .collect(Collectors.toList());
        Page<ForumDto> page = new PageImpl<>(forumClass, pageable, forumClass.size());
        return PageUtil.convert(page);
    }
    @Override
    public ApiPage<ForumDto> getAllClassForums(Pageable pageable) {
        Account account = securityUtil.getCurrentUser();
        ApiPage<ForumDto> classForums = null;
        if (account.getRole().getCode().equals(EAccountRole.STUDENT)) {
            classForums = getAllClassForumsOfStudent(account, pageable);
        } else if (account.getRole().getCode().equals(EAccountRole.TEACHER)) {
            classForums = getAllClassForumsOfTeacher(account, pageable);
        }
        return classForums;
    }

    private ApiPage<ForumDto> getAllClassForumsOfStudent(Account account, Pageable pageable) {

        List<ForumDto> forumClass = account.getStudentClasses().stream()
                .map(StudentClass::getaClass).distinct()
                .map(aClass -> this.getForumByClass(aClass.getId()))
                .collect(Collectors.toList());
        Page<ForumDto> page = new PageImpl<>(forumClass, pageable, forumClass.size());
        return PageUtil.convert(page);
    }

    private ApiPage<ForumDto> getAllClassForumsOfTeacher(Account account, Pageable pageable) {
        List<ForumDto> forumClass = account.getTeacherClass().stream()
                .map(clazz -> this.getForumByClass(clazz.getId()))
                .collect(Collectors.toList());
        Page<ForumDto> page = new PageImpl<>(forumClass, pageable, forumClass.size());
        return PageUtil.convert(page);
    }

    private Boolean isEnrolledToClassBelongToSubject(Account account, Subject subject) {
        List<Class> enrolledClass = account.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());
        Class classMatchSubject = enrolledClass.stream()
                .filter(aClass -> aClass.getCourse().getSubject() != null)
                .filter(aClass -> aClass.getCourse().getSubject().getId().equals(subject.getId()))
                .findFirst().orElseThrow(() -> ApiException.create(HttpStatus.CONFLICT).withMessage("Student not enrolled to this subject!!"));
        return true;
    }

    private Boolean isEnrolledToClass(Account account, Class clazz) {
        long enrolled = clazz.getStudentClasses().stream().filter(studentClass -> studentClass.getAccount().getId().equals(account.getId())).count();
        if (enrolled != 1) {
            return false;
        }
        return true;
    }
}
