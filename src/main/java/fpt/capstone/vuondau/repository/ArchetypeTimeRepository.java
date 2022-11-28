package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Archetype;
import fpt.capstone.vuondau.entity.ArchetypeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchetypeTimeRepository extends JpaRepository<ArchetypeTime, Long> {


}
