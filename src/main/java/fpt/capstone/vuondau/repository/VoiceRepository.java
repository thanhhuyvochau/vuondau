package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VoiceRepository extends JpaRepository<Voice,Long> {


}
