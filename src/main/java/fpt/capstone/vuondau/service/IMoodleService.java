package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.request.CreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleCategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleCourseResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;

import java.util.List;

public interface IMoodleService {


    List<MoodleCategoryResponse> getCategoryFromMoodle() throws JsonProcessingException;

    Boolean crateCategoryToMoodle(CreateCategoryRequest.CreateCategoryBody createCategoryBody) throws JsonProcessingException;

    ApiPage<MoodleCourseResponse> synchronizedClass() throws JsonProcessingException;
    Boolean synchronizedClassDetail() throws JsonProcessingException;
}
