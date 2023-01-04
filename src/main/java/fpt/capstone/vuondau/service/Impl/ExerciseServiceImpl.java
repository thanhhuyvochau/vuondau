package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.StudentClass;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EResourceMoodleType;
import fpt.capstone.vuondau.moodle.repository.MoodleCourseRepository;
import fpt.capstone.vuondau.moodle.request.GetMoodleCourseRequest;
import fpt.capstone.vuondau.moodle.response.MoodleModuleResponse;
import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;
import fpt.capstone.vuondau.moodle.response.MoodleResourceResponse;
import fpt.capstone.vuondau.moodle.response.MoodleSectionResponse;
import fpt.capstone.vuondau.service.IExerciseService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.http.HttpStatus;
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

        Account teacher = securityUtil.getCurrentUser();
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
}
