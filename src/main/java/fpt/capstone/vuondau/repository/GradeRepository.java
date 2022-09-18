package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
