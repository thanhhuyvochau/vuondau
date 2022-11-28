package fpt.capstone.vuondau.MoodleRepository.Request;

import java.util.List;

public class S1CourseRequest {

    private List<MoodleCourseDataRequest.MoodleCourseBody> courses ;



    public List<MoodleCourseDataRequest.MoodleCourseBody> getCourses() {
        return courses;
    }

    public void setCourses(List<MoodleCourseDataRequest.MoodleCourseBody> courses) {
        this.courses = courses;
    }
}
