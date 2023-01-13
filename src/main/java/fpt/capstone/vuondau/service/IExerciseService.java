package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;

import java.util.List;

public interface IExerciseService {


    List<MoodleRecourseDtoResponse> getExerciseInClass(Long classId);

    List<MoodleRecourseDtoResponse> teacherGetExerciseInClass(Long classId);
}
