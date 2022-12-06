package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.InfoFindTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoFindTutorRepository extends JpaRepository<InfoFindTutor,Long> {

}
