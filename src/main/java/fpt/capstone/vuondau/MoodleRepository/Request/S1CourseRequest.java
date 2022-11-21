package fpt.capstone.vuondau.MoodleRepository.Request;

import java.util.List;

public class S1CourseRequest {
    private String token;
    private String action ;
    private List<MoodleCourseDataRequest> courses ;

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

    public List<MoodleCourseDataRequest> getCourses() {
        return courses;
    }

    public void setCourses(List<MoodleCourseDataRequest> courses) {
        this.courses = courses;
    }
}
