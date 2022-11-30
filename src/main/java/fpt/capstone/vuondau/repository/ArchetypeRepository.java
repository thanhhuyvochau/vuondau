package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Archetype;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Long> {

    boolean existsByIdAndCode(Long id, String code);

    List<Archetype> findByCreatedByTeacherId(Long teacherId);
}
