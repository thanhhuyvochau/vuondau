package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.S1BaseSingleResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.S1CourseResponse;
import org.springframework.stereotype.Component;

@Component
public class MoodleCourseRepository extends S1BaseRepository {



    public MoodleCourseRepository(Caller caller) {
        super(caller);
    }

    public S1BaseSingleResponse<S1CourseResponse> postCourse(S1CourseRequest request) {
        TypeReference<S1BaseSingleResponse<S1CourseResponse>> typeReference = new TypeReference<S1BaseSingleResponse<S1CourseResponse>>() {
        };
        return caller.post(masterUri, request, typeReference);
    }


    public S1BaseSingleResponse<S1CourseResponse> getCourse(S1CourseRequest request) {
        TypeReference<S1BaseSingleResponse<S1CourseResponse>> typeReference = new TypeReference<S1BaseSingleResponse<S1CourseResponse>>() {
        };
        return caller.post(masterUri, request, typeReference);
    }


}
