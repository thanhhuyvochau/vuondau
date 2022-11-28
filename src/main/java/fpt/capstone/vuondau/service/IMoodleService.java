package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;

import java.util.List;

public interface IMoodleService {


    List<CategoryResponse> getCategoryFromMoodle() throws JsonProcessingException;

    Boolean crateCategoryToMoodle(MoodleCreateCategoryRequest.MoodleCreateCategoryBody moodleCreateCategoryBody) throws JsonProcessingException;

    ApiPage<MoodleClassResponse> synchronizedClass() throws JsonProcessingException;
}
