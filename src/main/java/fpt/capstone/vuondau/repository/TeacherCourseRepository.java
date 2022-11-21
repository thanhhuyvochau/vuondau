package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, TeacherCourseKey> {
    List<TeacherCourse> findAllByCourse (Course course ) ;


}
