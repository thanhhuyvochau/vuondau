package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CartItemTopicResponse {

    private CourseDetailResponse course ;

    public CourseDetailResponse getCourse() {
        return course;
    }

    public void setCourse(CourseDetailResponse course) {
        this.course = course;
    }
}
