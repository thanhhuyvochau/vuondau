package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.StudentClass;
import fpt.capstone.vuondau.moodle.repository.MoodleCourseRepository;
import fpt.capstone.vuondau.moodle.request.GetMoodleCourseRequest;
import fpt.capstone.vuondau.moodle.response.MoodleModuleResponse;
import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;
import fpt.capstone.vuondau.moodle.response.MoodleResourceResponse;
import fpt.capstone.vuondau.moodle.response.MoodleSectionResponse;
import fpt.capstone.vuondau.service.IExerciseService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
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
        Account student = securityUtil.getCurrentUser();
        List<Class> classes = student.getStudentClasses().stream().map(StudentClass::getaClass).collect(Collectors.toList());

        List<MoodleRecourseDtoResponse> exercise = new ArrayList<>();
        classes.forEach(aClass -> {
            if (aClass.getId().equals(classId)) {
                if (aClass.getMoodleClassId() != null) {
                    GetMoodleCourseRequest getMoodleCourseRequest = new GetMoodleCourseRequest();

                    getMoodleCourseRequest.setCourseid(aClass.getMoodleClassId());
                    try {
                        List<MoodleSectionResponse> resourceCourse = moodleCourseRepository.getResourceCourse(getMoodleCourseRequest);
                        resourceCourse.stream().skip(1).forEach(moodleRecourseClassResponse -> {
                            List<MoodleModuleResponse> modules = moodleRecourseClassResponse.getModules();
                            List<Boolean> isAssignList = modules.stream().map(moodleModuleResponse -> moodleModuleResponse.getModname().equals("assign") ||
                                    moodleModuleResponse.getModname().equals("quiz")).collect(Collectors.toList());
                            isAssignList.forEach(isAssign -> {
                                if (isAssign) {
                                    MoodleRecourseDtoResponse recourseDtoResponse = new MoodleRecourseDtoResponse();
                                    recourseDtoResponse.setId(moodleRecourseClassResponse.getId());
                                    recourseDtoResponse.setName(moodleRecourseClassResponse.getName());
                                    List<MoodleResourceResponse> moodleResourceResponseList = new ArrayList<>();
                                    modules.forEach(moodleResponse -> {
                                        if (moodleResponse.getModname().equals("assign")) {
                                            MoodleResourceResponse moodleResourceResponse = new MoodleResourceResponse();
                                            moodleResourceResponse.setId(moodleResponse.getId());
                                            moodleResourceResponse.setUrl(moodleResponse.getUrl());
                                            moodleResourceResponse.setName(moodleResponse.getName());
                                            moodleResourceResponse.setType(moodleResponse.getModname());
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
            }
        });
        return exercise;
    }
}
