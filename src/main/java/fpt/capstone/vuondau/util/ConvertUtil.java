package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.VoteNumberReponse;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.common.EGenderType;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.response.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {
    public static QuestionDto doConvertEntityToResponse(Question question, Account account) {
        QuestionDto questionDto = ObjectUtil.copyProperties(question, new QuestionDto(), QuestionDto.class, true);
        AccountSimpleResponse accountResponse = doConvertEntityToSimpleResponse(question.getStudent());
        // filter(comment -> comment.getParentComment() == null)
        // -> Để lấy tất cả các comment trực tiếp của câu hỏi (Không hỏi comment con) mục đích để get được ra dạng cây của các comment
        // -> Nếu không filter thì sẽ trả ra tất cả comment của câu hỏi và không handle ra dạng cây được.
        List<CommentDto> comments = question.getComments().stream().filter(comment -> comment.getParentComment() == null)
                .map(comment -> doConvertEntityToResponse(comment, account))
                .collect(Collectors.toList());
        questionDto.setVoteNumberReponse(VoteUtil.getVoteResponse(question));
        questionDto.setUser(accountResponse);
        questionDto.setComments(comments);
        Integer userState = VoteUtil.getUserState(question, account);
        questionDto.setUserState(userState);
        return questionDto;
    }

    public static QuestionSimpleDto doConvertEntityToSimpleResponse(Question question) {
        QuestionSimpleDto questionSimpleDto = ObjectUtil.copyProperties(question, new QuestionSimpleDto(), QuestionSimpleDto.class, true);
        AccountSimpleResponse accountResponse = doConvertEntityToSimpleResponse(question.getStudent());
        VoteNumberReponse voteNumberResponse = VoteUtil.getVoteResponse(question);
        questionSimpleDto.setVoteNumber(voteNumberResponse);
        questionSimpleDto.setUser(accountResponse);

        return questionSimpleDto;
    }

    public static CommentDto doConvertEntityToResponse(Comment comment, Account currentUser) {
        return doConvertEntityToResponseAsTree(comment, comment.getParentComment(), currentUser);
    }

    private static CommentDto doConvertEntityToResponseAsTree(Comment comment, Comment parentComment, Account currentUser) {
        CommentDto commentDto = ObjectUtil.copyProperties(comment, new CommentDto(), CommentDto.class, true);
        VoteNumberReponse voteResponse = VoteUtil.getVoteResponse(comment);
        commentDto.setVoteNumber(voteResponse);
        commentDto.setUserState(VoteUtil.getUserState(comment, currentUser));
        AccountSimpleResponse accountResponse = doConvertEntityToSimpleResponse(comment.getAccount());
        commentDto.setUser(accountResponse);
        if (parentComment != null) {
            CommentDto parentCommentDto = ObjectUtil.copyProperties(parentComment, new CommentDto(), CommentDto.class, true);
            commentDto.setParentComment(parentCommentDto);
        }
        if (!comment.getSubComments().isEmpty()) {
            List<CommentDto> subCommentDtos = comment.getSubComments().stream()
                    .map(subComment -> doConvertEntityToResponseAsTree(subComment, comment, currentUser))
                    .collect(Collectors.toList());
            commentDto.setSubComments(subCommentDtos);
        }
        return commentDto;
    }

    public static AccountResponse doConvertEntityToResponse(Account account) {

        AccountResponse accountResponse = new AccountResponse();
        if (account!=null) {
            accountResponse.setUsername(account.getUsername());
            accountResponse.setId(account.getId());

            RoleDto roleDto = doConvertEntityToResponse(account.getRole());
            accountResponse.setRole(roleDto);
            if (account.getResource() != null) {
                accountResponse.setAvatar(account.getResource().getUrl());
            }

            AccountDetail accountDetail = account.getAccountDetail();
            if (accountDetail != null) {
                EGenderType gender = accountDetail.getGender();
                if (gender != null) {
                    GenderResponse genderResponse = new GenderResponse();
                    genderResponse.setCode(gender.name());
                    genderResponse.setName(gender.getLabel());
                    accountResponse.setGender(genderResponse);
                }
                accountResponse.setPhoneNumber(accountDetail.getPhone());
                accountResponse.setBirthday(accountDetail.getBirthDay());
                accountResponse.setLastName(accountDetail.getLastName());
                accountResponse.setFirstName(accountDetail.getFirstName());
                accountResponse.setDomicile(accountDetail.getDomicile());
                accountResponse.setCurrentAddress(accountDetail.getCurrentAddress());
                accountResponse.setIdCard(accountDetail.getIdCard());
                accountResponse.setLevel(accountDetail.getLevel());
                accountResponse.setSchoolName(accountDetail.getTrainingSchoolName());
                accountResponse.setMajors(accountDetail.getMajors());
                accountResponse.setActive(accountDetail.getActive());
                accountResponse.setVoice(accountDetail.getVoice());
                accountResponse.setStatus(accountDetail.getStatus());
                accountResponse.setEmail(accountDetail.getEmail());
                accountResponse.setProvince(accountDetail.getTeachingProvince());


            }
        }


        return accountResponse;
    }

    public static AccountSimpleResponse doConvertEntityToSimpleResponse(Account account) {

        AccountSimpleResponse response = new AccountSimpleResponse();
        response.setId(account.getId());
        RoleDto roleDto = doConvertEntityToResponse(account.getRole());
        response.setRole(roleDto);
        if (account.getResource() != null) {
            response.setAvatar(account.getResource().getUrl());
        }

        AccountDetail accountDetail = account.getAccountDetail();
        if (accountDetail != null) {
            EGenderType gender = accountDetail.getGender();
            if (gender != null) {
                GenderResponse genderResponse = new GenderResponse();
                genderResponse.setCode(gender.name());
                genderResponse.setName(gender.getLabel());
                response.setGender(genderResponse);
            }
            response.setLastName(accountDetail.getLastName());
            response.setFirstName(accountDetail.getFirstName());
            response.setLevel(accountDetail.getLevel());
            response.setStatus(accountDetail.getStatus());

        }

        return response;
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

    public static SubjectSimpleResponse doConvertEntityToSimpleResponse(Subject subject) {
        SubjectSimpleResponse response = new SubjectSimpleResponse();
        response.setId(subject.getId());
        response.setCode(subject.getCode());
        response.setName(subject.getName());
        return response;
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
//        courseDetailResponse.setGrade(course.getGrade());
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
            List<ForumLessonDto> lessonDtos = forum.getForumLessons().stream().map(ConvertUtil::doConvertEntityToResponse)
                    .collect(Collectors.toList());
            forumDto.setForumLessonDtos(lessonDtos);
        } else {
            List<QuestionSimpleDto> questionDtos = forum.getQuestions().stream().map(ConvertUtil::doConvertEntityToSimpleResponse).collect(Collectors.toList());
            forumDto.setQuestions(questionDtos);
            SubjectSimpleResponse subject = doConvertEntityToSimpleResponse(forum.getSubject());
            forumDto.setSubject(subject);
        }
        return forumDto;
    }

    public static SimpleForumDto doConvertEntityToSimpleResponse(Forum forum) {
        SimpleForumDto forumDto = ObjectUtil.copyProperties(forum, new SimpleForumDto(), SimpleForumDto.class, true);
        if (forum.getType().name().equals(EForumType.CLASS.name())) {
            Class clazz = forum.getaClazz();
            forumDto.setClassName(clazz.getName());
            forumDto.setClassCode(clazz.getCode());
            forumDto.setClassId(clazz.getId());
        } else {
            Subject subject = forum.getSubject();
            forumDto.setSubjectCode(subject.getCode().name());
            forumDto.setSubjectName(subject.getName());
            forumDto.setSubjectId(subject.getId());
        }
        return forumDto;
    }

    public static ClassDto doConvertEntityToResponse(Class aclass) {
        ClassDto classDto = ObjectUtil.copyProperties(aclass, new ClassDto(), ClassDto.class, true);
        Course course = aclass.getCourse();
        CourseResponse courseResponse = ConvertUtil.doConvertCourseToCourseResponse(course);

        classDto.setStatus(aclass.getStatus());
        classDto.setStartDate(aclass.getStartDate());
        classDto.setEndDate(aclass.getEndDate());
        classDto.setNumberStudent(aclass.getNumberStudent());
        classDto.setMaxNumberStudent(aclass.getMaxNumberStudent());
        classDto.setEachStudentPayPrice(aclass.getEachStudentPayPrice());

        classDto.setTeacherReceivedPrice(aclass.getFinalPrice());


        if (aclass.getAccount() != null) {
            Account teacher = aclass.getAccount();
//            AccountResponse accountResponse = ObjectUtil.copyProperties(teacher, new AccountResponse(), AccountResponse.class);
            AccountSimpleResponse accountResponse1 = doConvertEntityToSimpleResponse(teacher);
            classDto.setTeacher(accountResponse1);
//            accountResponse.setBirthday(teacher.getBirthday());
//            accountResponse.setIntroduce(teacher.getIntroduce());
//            accountResponse.setPhoneNumber(teacher.getPhoneNumber());
//            EGenderType gender = teacher.getGender();
//            accountResponse.setGenderResponse(teacher.getGender());
//            classDto.setTeacher(ObjectUtil.copyProperties(aclass.getAccount(), new AccountResponse(), AccountResponse.class));

        }


        classDto.setCourse(courseResponse);
        return classDto;
    }

    public static ForumLessonDto doConvertEntityToResponse(ForumLesson forumLesson) {
        ForumLessonDto forumLessonDto = ObjectUtil.copyProperties(forumLesson, new ForumLessonDto(), ForumLessonDto.class, true);
        List<QuestionSimpleDto> questionDtos = forumLesson.getQuestions().stream().map(ConvertUtil::doConvertEntityToSimpleResponse).collect(Collectors.toList());
        forumLessonDto.setQuestions(questionDtos);
        return forumLessonDto;
    }

    public static CandicateResponse doConvertEntityToResponse(ClassTeacherCandicate classTeacherCandicate) {
        CandicateResponse response = new CandicateResponse();
        AccountResponse teacher = doConvertEntityToResponse(classTeacherCandicate.getTeacher());
        response.setTeacher(teacher);
        response.setStatus(classTeacherCandicate.getStatus());
        return response;
    }
}