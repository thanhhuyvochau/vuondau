package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.request.CourseIdRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodleCourseRepository extends S1BaseRepository {


    public MoodleCourseRepository(Caller caller) {
        super(caller);
    }

    public List<MoodleClassResponse> postCourse(S1CourseRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleClassResponse>> typeReference = new TypeReference<List<MoodleClassResponse>>() {
        };
        return caller.postWithAuthenticationTokeCustom(createCourseUri, request, typeReference);
    }


    public List<MoodleClassResponse> getCourse(MoodleMasterDataRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleClassResponse>> typeReference = new TypeReference<List<MoodleClassResponse>>() {
        };
        return caller.getWithAuthenticationTokeCustom(courseUri, request, typeReference);
    }


    public List<CategoryResponse> getCategory(MoodleCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<CategoryResponse>> typeReference = new TypeReference<List<CategoryResponse>>() {
        };
        return caller.getWithAuthenticationTokeCustom(categoryUri, request, typeReference);
    }


    public List<MoodleClassResponse> postCategory(MoodleCreateCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<MoodleClassResponse>> typeReference = new TypeReference<List<MoodleClassResponse>>() {
        };
        return caller.postWithAuthenticationTokeCustom(createCategoryUri, request, typeReference);
    }


    public List<MoodleRecourseClassResponse> getResourceCourse(CourseIdRequest classId) throws JsonProcessingException {
        TypeReference<List<MoodleRecourseClassResponse>> typeReference = new TypeReference<List<MoodleRecourseClassResponse>>() {
        };
        return caller.postWithAuthenticationTokeCustom(resourceUri, classId, typeReference);
    }
//    public List<MoodleSectionResponse>
}
