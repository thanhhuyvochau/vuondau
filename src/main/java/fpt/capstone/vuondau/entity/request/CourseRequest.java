package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.TeacherCourse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRequest {

    private String name;

    private String code;

    private Long gradeId;

    private Long subjectId;

    private List<Long> teacherCourses = new ArrayList<>();
}
