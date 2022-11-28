package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.DayOfWeek;
import fpt.capstone.vuondau.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Long> {
//        List<DayOfWeek> findAllByIds (List<Long> ids);
}
