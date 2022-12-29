package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.MoodleRepository.request.GetCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.request.CreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.response.CourseResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleSectionResponse;
import fpt.capstone.vuondau.MoodleRepository.request.GetCourseRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodleCourseRepository extends S1BaseRepository {


    public MoodleCourseRepository(Caller caller) {
        super(caller);
    }

    public List<CourseResponse> createCourse(S1CourseRequest request) throws JsonProcessingException {
        TypeReference<List<CourseResponse>> typeReference = new TypeReference<List<CourseResponse>>() {
        };
        return caller.post(createCourseUri, request, typeReference);
    }


    public List<CourseResponse> getCourses(MoodleMasterDataRequest request) throws JsonProcessingException {        TypeReference<List<CourseResponse>> typeReference = new TypeReference<List<CourseResponse>>() {
        };
        return caller.get(courseUri, request, typeReference);
    }


    public List<CategoryResponse> getCategories(GetCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<CategoryResponse>> typeReference = new TypeReference<List<CategoryResponse>>() {
        };
        return caller.get(categoryUri, request, typeReference);
    }


    public List<CourseResponse> createCategory(CreateCategoryRequest request) throws JsonProcessingException {
        TypeReference<List<CourseResponse>> typeReference = new TypeReference<List<CourseResponse>>() {
        };
        return caller.post(createCategoryUri, request, typeReference);
    }


    public List<MoodleSectionResponse> getResourceCourse(GetCourseRequest classId) throws JsonProcessingException {
        TypeReference<List<MoodleSectionResponse>> typeReference = new TypeReference<List<MoodleSectionResponse>>() {
        };
        return caller.post(resourceUri, classId, typeReference);
    }
}
