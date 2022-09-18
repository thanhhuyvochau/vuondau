package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.TeacherCourse;
import fpt.capstone.vuondau.entity.TeacherCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, TeacherCourseKey> {
}
