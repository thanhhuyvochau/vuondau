package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.ArchetypeTime;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByStudentAndModule_Section_Clazz(Account student, Class clazz);
}
