package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.dto.TeacherCourseDto;
import fpt.capstone.vuondau.entity.common.EGradeType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class CourseDetailResponse {
    private Long id;
    private String name;

    private String code;

    private String title;

    private boolean isActive ;

    private String description ;

    private EGradeType grade;

    @Schema(description = "hình đại diện course")
    private String image;

    @Schema(description = "Giá tiền của course")
    private BigDecimal unitPrice;


    private List<ClassDto> clazz ;

    private SubjectDto subject;

    private List<TeacherCourseDto> teacherCourse ;

    private  List<MoodleRecourseClassResponse> resources ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<TeacherCourseDto> getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(List<TeacherCourseDto> teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    public EGradeType getGrade() {
        return grade;
    }

    public void setGrade(EGradeType grade) {
        this.grade = grade;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ClassDto> getClazz() {
        return clazz;
    }

    public void setClazz(List<ClassDto> clazz) {
        this.clazz = clazz;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }


    public List<MoodleRecourseClassResponse> getResources() {
        return resources;
    }

    public void setResources(List<MoodleRecourseClassResponse> resources) {
        this.resources = resources;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
