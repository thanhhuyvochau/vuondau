package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Answer;
import fpt.capstone.vuondau.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer,Long> {



}
