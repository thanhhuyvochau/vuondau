package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

}
