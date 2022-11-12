package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Answer;
import fpt.capstone.vuondau.entity.SurveyQuestion;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {


  Optional<Answer> findByIdAndSurveyQuestion(Long answerId , SurveyQuestion surveyQuestion );
}
