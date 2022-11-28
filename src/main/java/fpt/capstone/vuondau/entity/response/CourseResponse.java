package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.EGradeType;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.dto.TeacherCourseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class CourseResponse {

    @Schema(description = "id course")
    private Long id;

    @Schema(description = "hình đại diện course")
    private String image;

    @Schema(description = "title của course")
    private String courseTitle;

    @Schema(description = "Têncủa course")
    private String courseName;


    @Schema(description = "Tên giáo viên")
    private String teacherName;


//    @Schema(description = "Giá tiền của course")
//    private BigDecimal unitPriceCourse;
//
//    @Schema(description = "Giá tiền của course")
//    private BigDecimal finalPriceCourse;

    private SubjectDto subject ;


    private Long totalClass ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

//    public BigDecimal getUnitPriceCourse() {
//        return unitPriceCourse;
//    }
//
//    public void setUnitPriceCourse(BigDecimal unitPriceCourse) {
//        this.unitPriceCourse = unitPriceCourse;
//    }
//
//    public BigDecimal getFinalPriceCourse() {
//        return finalPriceCourse;
//    }
//
//    public void setFinalPriceCourse(BigDecimal finalPriceCourse) {
//        this.finalPriceCourse = finalPriceCourse;
//    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public Long getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Long totalClass) {
        this.totalClass = totalClass;
    }
}
