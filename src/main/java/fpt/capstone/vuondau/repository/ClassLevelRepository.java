package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Answer;
import fpt.capstone.vuondau.entity.ClassLevel;
import fpt.capstone.vuondau.entity.SurveyQuestion;
import fpt.capstone.vuondau.entity.common.EClassLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClassLevelRepository extends JpaRepository<ClassLevel, Long> {

  Optional<ClassLevel> findByCode(EClassLevel classLevel);


}
