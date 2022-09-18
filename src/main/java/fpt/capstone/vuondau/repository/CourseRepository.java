package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> searchCourseByNameContainsIgnoreCase(String name);
}
