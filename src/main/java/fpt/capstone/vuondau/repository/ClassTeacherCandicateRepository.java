package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.ClassTeacherCandicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTeacherCandicateRepository extends JpaRepository<ClassTeacherCandicate, Long> {
    Page<ClassTeacherCandicate> findAllByClazz(Class clazz, Pageable pageable);
}
