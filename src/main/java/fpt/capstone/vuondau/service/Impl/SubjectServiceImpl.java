package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.request.SubjectSearchRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.StudentAnswerRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import fpt.capstone.vuondau.service.ISubjectService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.SubjectSpecificationBuilder;
import fpt.capstone.vuondau.util.specification.SuggestSubjectSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectServiceImpl implements ISubjectService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final StudentAnswerRepository studentAnswerRepository;

    public SubjectServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, AccountRepository accountRepository, MessageUtil messageUtil, StudentAnswerRepository studentAnswerRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.studentAnswerRepository = studentAnswerRepository;
    }

    @Override
    public SubjectResponse createNewSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setCode(subjectRequest.getCode());
        subject.setName(subject.getName());
        List<Course> allCourse = courseRepository.findAllById(subjectRequest.getCourseIds());
//        subject.setCourses(allCourse);

        Subject subjectSaved = subjectRepository.save(subject);
        SubjectResponse response = new SubjectResponse();
        response.setId(subjectSaved.getId());
        response.setName(subjectSaved.getName());
        response.setCode(subjectSaved.getCode());
//        response.setCourseIds(subjectSaved.getCourses());

        return response;
    }

    @Override
    public SubjectResponse updateSubject(Long subjectId, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        subject.setCode(subjectRequest.getCode());
        subject.setName(subject.getName());
        List<Course> allCourse = courseRepository.findAllById(subjectRequest.getCourseIds());
//        subject.setCourses(allCourse);
        Subject subjectSaved = subjectRepository.save(subject);

        SubjectResponse response = new SubjectResponse();
        response.setId(subjectSaved.getId());
        response.setName(subjectSaved.getName());
        response.setCode(subjectSaved.getCode());
//        response.setCourseIds(subjectSaved.getCourses());
        return response;
    }

    @Override
    public Long deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        subjectRepository.delete(subject);
        return subjectId;
    }

    @Override
    public SubjectResponse getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay subject"));
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setCode(subject.getCode());
//        response.setCourseIds(subject.getCourses());
        return response;
    }

    @Override
    public ApiPage<SubjectResponse> suggestSubjectForStudent(Long studentId, Pageable pageable) {
        Account account = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + studentId));

        List<StudentAnswer> studentAnswers = account.getStudentAnswers();

        List<Answer> answer = studentAnswers.stream().map(StudentAnswer::getAnswer).collect(Collectors.toList());

        List<String> stringAnswer = answer.stream().map(Answer::getAnswer).collect(Collectors.toList());
        String queryString = String.join(" ", stringAnswer);


//        SuggestSubjectSpecificationBuilder builder = SuggestSubjectSpecificationBuilder.specification()
//                .querySubjectCode(queryString) ;


        Optional<String> first = answer.stream().map(Answer::getAnswer).findFirst();
        String s = "";
        if (first.isPresent()) {
            s = first.get();
        }
        SuggestSubjectSpecificationBuilder builder = SuggestSubjectSpecificationBuilder.specification()
                .querySubjectCode(s);


        Page<Subject> subjectPage = subjectRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(subjectPage.map(ConvertUtil::doConvertEntityToResponse));
    }

    @Override
    public List<SubjectResponse> getAllWithoutPaging() {

        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(ConvertUtil::doConvertEntityToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public ApiPage<SubjectResponse> getAllWithPaging(Pageable pageable) {
        Page<Subject> allSubjects = subjectRepository.findAll(pageable);
        return PageUtil.convert(allSubjects.map(ConvertUtil::doConvertEntityToResponse));
    }

    @Override
    public ApiPage<SubjectResponse> searchSubject(SubjectSearchRequest query, Pageable pageable) {
        SubjectSpecificationBuilder builder = SubjectSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Subject> coursePage = subjectRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(coursePage.map(ConvertUtil::doConvertEntityToResponse));

    }
}
