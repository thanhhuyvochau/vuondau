package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.entity.request.RequestSearchRequest;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExerciseService {


    List<MoodleRecourseDtoResponse> getExerciseInClass(Long classId);
}
