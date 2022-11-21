package fpt.capstone.vuondau.MoodleRepository.Request;

public class S1CourseCreateRequest {
    private String token;
    private String action ;
    private MoodleCourseDataRequest courseDataRequest ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MoodleCourseDataRequest getCourseDataRequest() {
        return courseDataRequest;
    }

    public void setCourseDataRequest(MoodleCourseDataRequest courseDataRequest) {
        this.courseDataRequest = courseDataRequest;
    }
}
