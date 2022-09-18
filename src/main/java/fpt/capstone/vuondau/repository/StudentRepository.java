package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
