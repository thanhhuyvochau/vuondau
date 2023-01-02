package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.MoodleRepository.request.*;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleCategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleCourseResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleSectionResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodleCourseRepository extends S1BaseRepository {


    public MoodleCourseRepository(Caller caller) {
        super(caller);
    }

    public List<MoodleCourseResponse> createCourse(S1CourseRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleCourseResponse>> typeReference = new TypeReference<List<MoodleCourseResponse>>() {
        };
        return caller.post(createCourseUri, request, typeReference);
    }


    public List<MoodleCourseResponse> getCourses(MoodleMasterDataRequest request) throws JsonProcessingException {        TypeReference<List<MoodleCourseResponse>> typeReference = new TypeReference<List<MoodleCourseResponse>>() {
        };
        return caller.get(courseUri, request, typeReference);
    }


    public List<MoodleCategoryResponse> getCategories(GetCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleCategoryResponse>> typeReference = new TypeReference<List<MoodleCategoryResponse>>() {
        };
        return caller.get(categoryUri, request, typeReference);
    }


    public List<MoodleCourseResponse> createCategory(CreateCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleCourseResponse>> typeReference = new TypeReference<List<MoodleCourseResponse>>() {
        };
        return caller.post(createCategoryUri, request, typeReference);
    }


    public List<MoodleSectionResponse> getResourceCourse(GetMoodleCourseRequest classId) throws JsonProcessingException {
        TypeReference<List<MoodleSectionResponse>> typeReference = new TypeReference<List<MoodleSectionResponse>>() {
        };
        return caller.post(resourceUri, classId, typeReference);
    }
}
