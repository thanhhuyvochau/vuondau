package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.moodle.request.CreateCategoryRequest;
import fpt.capstone.vuondau.moodle.response.MoodleCategoryResponse;
import fpt.capstone.vuondau.moodle.response.MoodleCourseResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;

import java.util.List;

public interface IMoodleService {


    List<MoodleCategoryResponse> getCategoryFromMoodle() throws JsonProcessingException;

    Boolean createCategoryToMoodle(CreateCategoryRequest.CreateCategoryBody createCategoryBody) throws JsonProcessingException;

    ApiPage<MoodleCourseResponse> synchronizedClassFromMoodle() throws JsonProcessingException;

    Boolean synchronizedClassDetailFromMoodle() throws JsonProcessingException;

    String enrolUserToCourseMoodle(Class clazz) throws JsonProcessingException;

    String unenrolUserToCourseMoodle(Account account, Class clazz);

    Boolean synchronizedRoleFromMoodle() throws JsonProcessingException;
}
