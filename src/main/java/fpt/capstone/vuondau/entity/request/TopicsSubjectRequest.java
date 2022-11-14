package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.dto.CourseDto;

import java.util.List;

public class TopicsSubjectRequest {

    private Long subjectId ;
    private List<CourseDto> courses ;




    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }
}
