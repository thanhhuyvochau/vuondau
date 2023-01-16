package fpt.capstone.vuondau.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.StudentClass;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.moodle.repository.MoodleCourseRepository;
import fpt.capstone.vuondau.moodle.request.GetMoodleAssignmentIdsCourseRequest;
import fpt.capstone.vuondau.moodle.request.GetMoodleCourseRequest;
import fpt.capstone.vuondau.moodle.response.*;
import fpt.capstone.vuondau.service.IExerciseService;
import fpt.capstone.vuondau.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExerciseServiceImpl implements IExerciseService {


    private final MessageUtil messageUtil;

    private final SecurityUtil securityUtil;

    private final MoodleCourseRepository moodleCourseRepository;


    public ExerciseServiceImpl(MessageUtil messageUtil, SecurityUtil securityUtil, MoodleCourseRepository moodleCourseRepository) {
        this.messageUtil = messageUtil;
        this.securityUtil = securityUtil;
        this.moodleCourseRepository = moodleCourseRepository;
    }

    @Override
    public List<MoodleRecourseDtoResponse> getExerciseInClass(Long classId) {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();
        List<Class> classes = student.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());

        List<MoodleRecourseDtoResponse> exercise = new ArrayList<>();
        for (Class aClass : classes) {
            if (aClass.getId().equals(classId)) {
                if (aClass.getMoodleClassId() != null) {
                    exercise = ConvertUtil.doConvertExercise(aClass);
                }
            }
        }
        return exercise;
    }

    @Override
    public List<MoodleRecourseDtoResponse> teacherGetExerciseInClass(Long classId) {

        Account teacher = securityUtil.getCurrentUserThrowNotFoundException();
        List<Class> classes = teacher.getTeacherClass();

        List<MoodleRecourseDtoResponse> exercise = new ArrayList<>();
        for (Class aClass : classes) {
            if (aClass.getId().equals(classId)) {
                if (aClass.getMoodleClassId() != null) {
                    exercise = ConvertUtil.doConvertExercise(aClass);
                }
            }
        }
        return exercise;
    }

    @Override
    public ApiPage<MoodleRecourseClassesDtoResponse> studentGetAllExerciseAllClass(Pageable pageable) {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();

        List<MoodleRecourseClassesDtoResponse> moodleRecourseClassesDtoResponses = new ArrayList<>();
        List<Class> classes = student.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());
        for (Class aClass : classes) {
            if (aClass.getMoodleClassId() != null) {
                moodleRecourseClassesDtoResponses.add(setDataFormMoodleRecourseClasses(aClass, student));
            }
        }
        Page<MoodleRecourseClassesDtoResponse> page = new PageImpl<>(moodleRecourseClassesDtoResponses, pageable, moodleRecourseClassesDtoResponses.size());

        return PageUtil.convert(page);

    }

    @Override
    public ApiPage<MoodleAllClassRecourseDtoResponse> teacherGetAllExerciseAllClass(Pageable pageable) {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();
        List<Class> teacherClass = student.getTeacherClass();
        List<MoodleAllClassRecourseDtoResponse> responseList = new ArrayList<>();
        List<MoodleRecourseDtoResponse> exercise = new ArrayList<>();
        for (Class aClass : teacherClass) {
            MoodleAllClassRecourseDtoResponse response = new MoodleAllClassRecourseDtoResponse();
            response.setClassId(aClass.getId());
            response.setClassName(aClass.getName());
            response.setCodeName(aClass.getCode());
            if (aClass.getMoodleClassId() != null) {
                exercise = ConvertUtil.doConvertExercise(aClass);
                response.setResources(exercise);
            }
            responseList.add(response);
        }
        Page<MoodleAllClassRecourseDtoResponse> page = new PageImpl<>(responseList, pageable, responseList.size());
        return PageUtil.convert(page);

    }

    private MoodleRecourseClassesDtoResponse setDataFormMoodleRecourseClasses(Class aClass, Account student) {

        MoodleRecourseClassesDtoResponse response = new MoodleRecourseClassesDtoResponse();
        List<MoodleRecourseDtoResponse> exercise = new ArrayList<>();
        if (aClass.getMoodleClassId() != null) {
            GetMoodleCourseRequest getMoodleCourseRequest = new GetMoodleCourseRequest();

            response.setClassId(aClass.getId());

            getMoodleCourseRequest.setCourseid(aClass.getMoodleClassId());
            try {
                List<MoodleSectionResponse> resourceCourse = moodleCourseRepository.getResourceCourse(getMoodleCourseRequest);
                resourceCourse.stream().skip(1).forEach(moodleRecourseClassResponse -> {

                    List<MoodleModuleResponse> modules = moodleRecourseClassResponse.getModules();
                    List<Boolean> isAssignList = modules.stream().map(moodleModuleResponse -> moodleModuleResponse.getModname().equals("quiz")
                            || moodleModuleResponse.getModname().equals("assign")
                    ).collect(Collectors.toList());
                    GetMoodleAssignmentIdsCourseRequest request = new GetMoodleAssignmentIdsCourseRequest();
                    List<Long> idsInstance = new ArrayList<>();

                    isAssignList.forEach(isAssign -> {
                        if (isAssign) {
                            MoodleRecourseDtoResponse recourseDtoResponse = new MoodleRecourseDtoResponse();
                            recourseDtoResponse.setId(moodleRecourseClassResponse.getId());
                            recourseDtoResponse.setName(moodleRecourseClassResponse.getName());
                            List<MoodleResourceResponse> moodleResourceResponseList = new ArrayList<>();
                            modules.forEach(moodleResponse -> {

                                if (moodleResponse.getModname().equals("quiz") || moodleResponse.getModname().equals("assign")) {
                                    MoodleResourceResponse moodleResourceResponse = new MoodleResourceResponse();
                                    moodleResourceResponse.setId(moodleResponse.getId());
                                    moodleResourceResponse.setUrl(moodleResponse.getUrl());
                                    moodleResourceResponse.setName(moodleResponse.getName());
                                    moodleResourceResponse.setType(moodleResponse.getModname());


                                    idsInstance.add(moodleResponse.getInstance());
                                    request.setAssignmentids(idsInstance);
                                    try {
                                        moodleResourceResponse.setSubmissions(setDateAssignmentsResourceCourse(request, student));
                                        ;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    moodleResourceResponseList.add(moodleResourceResponse);
                                }
                            });
                            recourseDtoResponse.setModules(moodleResourceResponseList);
                            exercise.add(recourseDtoResponse);
                        }
                    });

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        response.setModules(exercise);
        return response;
    }

    private List<MoodleAssignmentsResponse.assignments.submissions> setDateAssignmentsResourceCourse(GetMoodleAssignmentIdsCourseRequest request, Account student) throws JsonProcessingException {
        List<MoodleAssignmentsResponse.assignments.submissions> submissions = new ArrayList<>();
        List<MoodleAssignmentsResponse> assignments = moodleCourseRepository.getAssignmentsResourceCourse(request);

        for (MoodleAssignmentsResponse moodleAssignmentsResponse : assignments) {
            List<MoodleAssignmentsResponse.assignments> assignments2 = moodleAssignmentsResponse.getAssignments();
            for (MoodleAssignmentsResponse.assignments assignments1 : assignments2) {
                List<MoodleAssignmentsResponse.assignments.submissions> submissions1 = assignments1.getSubmissions();
                for (MoodleAssignmentsResponse.assignments.submissions submission : submissions1) {
                    int userid = submission.getUserid().intValue();
                    if (student.getMoodleUserId() == userid) {
                        MoodleAssignmentsResponse.assignments.submissions submissions3 = ObjectUtil.copyProperties(submission, new MoodleAssignmentsResponse.assignments.submissions(), MoodleAssignmentsResponse.assignments.submissions.class);
                        submissions.add(submissions3);
                    }


                }
            }

        }
        return submissions;
    }


}
