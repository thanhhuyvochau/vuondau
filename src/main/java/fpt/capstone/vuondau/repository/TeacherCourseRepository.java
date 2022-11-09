package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.TeacherCourse;
import fpt.capstone.vuondau.entity.TeacherCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, TeacherCourseKey> {
    List<TeacherCourse> findAllByCourse (Course course ) ;
}
