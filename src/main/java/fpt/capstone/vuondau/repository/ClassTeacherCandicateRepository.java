package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.ClassTeacherCandicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTeacherCandicateRepository extends JpaRepository<ClassTeacherCandicate, Long> {
}
