package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
}
