package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTypeRepository extends JpaRepository<ClassType,Long> {
}
