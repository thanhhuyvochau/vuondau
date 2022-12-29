package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.request.CreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.response.CourseResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;

import java.util.List;

public interface IMoodleService {


    List<CategoryResponse> getCategoryFromMoodle() throws JsonProcessingException;

    Boolean crateCategoryToMoodle(CreateCategoryRequest.CreateCategoryBody createCategoryBody) throws JsonProcessingException;

    ApiPage<CourseResponse> synchronizedClass() throws JsonProcessingException;
    Boolean synchronizedClassDetail() throws JsonProcessingException;
}
