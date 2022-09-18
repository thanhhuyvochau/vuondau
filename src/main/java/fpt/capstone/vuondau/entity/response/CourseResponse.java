package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.TeacherCourse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseResponse {
    
    
    
    private Long id;

    
    private String name;

    
    private String code;

    
    
    private GradeResponse grade;

    
    
    private SubjectResponse subject;


    
    private List<TeacherCourse> teacherCourses = new ArrayList<>() ;
}
