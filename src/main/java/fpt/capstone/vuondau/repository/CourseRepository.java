package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> searchCourseByNameContainsIgnoreCase(String name);

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);
}
