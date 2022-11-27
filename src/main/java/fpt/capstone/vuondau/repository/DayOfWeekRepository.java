package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.DayOfWeek;
import fpt.capstone.vuondau.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Long> {

}
