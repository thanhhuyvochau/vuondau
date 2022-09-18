package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
