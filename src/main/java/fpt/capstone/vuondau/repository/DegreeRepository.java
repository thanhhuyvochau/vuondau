package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Degree;
import fpt.capstone.vuondau.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
}
