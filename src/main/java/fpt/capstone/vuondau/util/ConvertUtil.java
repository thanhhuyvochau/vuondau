package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.common.EGenderType;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.response.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {
    public static QuestionDto doConvertEntityToResponse(Question question) {
        QuestionDto questionDto = ObjectUtil.copyProperties(question, new QuestionDto(), QuestionDto.class, true);
        AccountResponse accountResponse = doConvertEntityToResponse(question.getStudent());
        // filter(comment -> comment.getParentComment() == null)
        // -> Để lấy tất cả các comment trực tiếp của câu hỏi (Không hỏi comment con) mục đích để get được ra dạng cây của các comment
        // -> Nếu không filter thì sẽ trả ra tất cả comment của câu hỏi và không handle ra dạng cây được.
        List<CommentDto> comments = question.getComments().stream().filter(comment -> comment.getParentComment() == null)
                .map(ConvertUtil::doConvertEntityToResponse)
                .collect(Collectors.toList());
        questionDto.setStudent(accountResponse);
        questionDto.setComments(comments);
        return questionDto;
    }

    public static CommentDto doConvertEntityToResponse(Comment comment) {
        return doConvertEntityToResponseAsTree(comment, comment.getParentComment());
    }

    private static CommentDto doConvertEntityToResponseAsTree(Comment comment, Comment parentComment) {
        CommentDto commentDto = ObjectUtil.copyProperties(comment, new CommentDto(), CommentDto.class, true);
        AccountResponse accountResponse = doConvertEntityToResponse(comment.getAccount());
        commentDto.setStudent(accountResponse);
        if (parentComment != null) {
            CommentDto parentCommentDto = ObjectUtil.copyProperties(parentComment, new CommentDto(), CommentDto.class, true);
            commentDto.setParentComment(parentCommentDto);
        }
        if (!comment.getSubComments().isEmpty()) {
            List<CommentDto> subCommentDtos = comment.getSubComments().stream()
                    .map(subComment -> doConvertEntityToResponseAsTree(subComment, comment))
                    .collect(Collectors.toList());
            commentDto.setSubComments(subCommentDtos);
        }
        return commentDto;
    }

    public static AccountResponse doConvertEntityToResponse(Account account) {
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class, true);
        EGenderType gender = account.getGender();
        if (gender != null) {
            GenderResponse genderResponse = new GenderResponse();
            genderResponse.setCode(gender.name());
            genderResponse.setName(gender.getLabel());
            accountResponse.setGenderResponse(genderResponse);
        }
        RoleDto roleDto = doConvertEntityToResponse(account.getRole());
        accountResponse.setRole(roleDto);
        if (account.getResource() != null) {
            accountResponse.setAvatar(account.getResource().getUrl());
        }

        return accountResponse;
    }

    public static RoleDto doConvertEntityToResponse(Role role) {
        return ObjectUtil.copyProperties(role, new RoleDto(), RoleDto.class, true);
    }

    public static SubjectResponse doConvertEntityToResponse(Subject subject) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setCode(subject.getCode());
        subjectResponse.setName(subject.getName());
        List<Long> idCourse = subject.getCourses().stream().map(Course::getId).collect(Collectors.toList());
        subjectResponse.setCourseIds(idCourse);
        return subjectResponse;
    }

    public static RequestFormResponese doConvertEntityToResponse(Request request) {
        RequestFormResponese requestFormResponese = ObjectUtil.copyProperties(request, new RequestFormResponese(), RequestFormResponese.class);
        requestFormResponese.setRequestType(ObjectUtil.copyProperties(request.getRequestType(), new RequestTypeDto(), RequestTypeDto.class));
        return requestFormResponese;
    }

    public static AccountDetailResponse doConvertEntityToResponse(AccountDetail accountDetail) {

        AccountDetailResponse accountDetailResponse = ObjectUtil.copyProperties(accountDetail, new AccountDetailResponse(), AccountDetailResponse.class);

        List<AccountDetailSubject> accountDetailSubjects = accountDetail.getAccountDetailSubjects();
        List<SubjectDto> subjects = new ArrayList<>();
        accountDetailSubjects.forEach(accountDetailSubject -> {
            subjects.add(ObjectUtil.copyProperties(accountDetailSubject.getSubject(), new SubjectDto(), SubjectDto.class));
        });
        accountDetailResponse.setSubjects(subjects);
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        List<Resource> resources = accountDetail.getResources();
        resources.forEach(resource -> {
            resourceDtoList.add(ObjectUtil.copyProperties(resource, new ResourceDto(), ResourceDto.class));
        });

        accountDetailResponse.setResources(resourceDtoList);
        accountDetailResponse.setActive(accountDetail.getActive());
        return accountDetailResponse;

    }

    public static CourseDetailResponse doConvertEntityToResponse(Course course) {
        CourseDetailResponse courseDetailResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class);
        courseDetailResponse.setGrade(course.getGrade());
        return courseDetailResponse;
    }

    public static CourseResponse doConvertCourseToCourseResponse(Course course) {
        if (course == null) {
            return null;
        }
        CourseResponse courseResponse = ObjectUtil.copyProperties(course, new CourseResponse(), CourseResponse.class);
        courseResponse.setCourseName(course.getName());
        if (course.getResource() != null) {
            courseResponse.setImage(course.getResource().getUrl());
        }
        if (course.getSubject() != null) {
            courseResponse.setSubject(ConvertUtil.doConvertEntityToResponse(course.getSubject()));
        }
        courseResponse.setCourseTitle(course.getTitle());

//        courseDetailResponse.setGrade(course.getGrade());
        return courseResponse;
    }


    public static ForumDto doConvertEntityToResponse(Forum forum) {
        ForumDto forumDto = ObjectUtil.copyProperties(forum, new ForumDto(), ForumDto.class, true);
        if (forum.getType().name().equals(EForumType.CLASS.name())) {
            ClassDto classDto = doConvertEntityToResponse(forum.getaClazz());
            forumDto.setaClass(classDto);
            List<ForumLessonDto> lessonDtos = forum.getForumLessons().stream().map(ConvertUtil::doConvertEntityToResponse).collect(Collectors.toList());
            forumDto.setForumLessonDtos(lessonDtos);
        } else {
            List<QuestionDto> questionDtos = forum.getQuestions().stream().map(ConvertUtil::doConvertEntityToResponse).collect(Collectors.toList());
            forumDto.setQuestions(questionDtos);
        }
        return forumDto;
    }

    public static ClassDto doConvertEntityToResponse(Class aclass) {
        ClassDto classDto = ObjectUtil.copyProperties(aclass, new ClassDto(), ClassDto.class,true);
        Course course = aclass.getCourse();
        CourseResponse courseResponse = ConvertUtil.doConvertCourseToCourseResponse(course);
        classDto.setStatus(aclass.getStatus());
        classDto.setStartDate(aclass.getStartDate());
        classDto.setEndDate(aclass.getEndDate());
        classDto.setNumberStudent(aclass.getNumberStudent());
        classDto.setMaxNumberStudent(aclass.getMaxNumberStudent());
        classDto.setUnitPrice(aclass.getUnitPrice());
        classDto.setFinalPrice(aclass.getFinalPrice());
        Account teacher = aclass.getAccount();
        classDto.setTeacher(ObjectUtil.copyProperties(teacher, new AccountResponse(), AccountResponse.class,true));
        classDto.setCourse(courseResponse);
        return classDto;
    }

    public static ForumLessonDto doConvertEntityToResponse(ForumLesson forumLesson) {
        ForumLessonDto forumLessonDto = ObjectUtil.copyProperties(forumLesson, new ForumLessonDto(), ForumLessonDto.class, true);
        List<QuestionDto> questionDtos = forumLesson.getQuestions().stream().map(ConvertUtil::doConvertEntityToResponse).collect(Collectors.toList());
        forumLessonDto.setQuestions(questionDtos);
        return forumLessonDto;
    }
}
