package fpt.capstone.vuondau.entity.response;


import fpt.capstone.vuondau.entity.Course;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

public class GradeResponse implements Serializable {

    @Schema(description = "ID grade")
    private Long id;
    @Schema(description = "Mã  grade")
    private String code;
    @Schema(description = "Tên subject")
    private String name ;

    @Schema(description = "id course ")
    private List<Course> courseIds ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Course> courseIds) {
        this.courseIds = courseIds;
    }
}