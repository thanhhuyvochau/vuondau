package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.EModuleType;
import fpt.capstone.vuondau.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
    List<Module> findAllByType(EModuleType eModuleType ) ;
}
