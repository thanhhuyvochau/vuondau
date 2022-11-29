package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllBySubject_Id(Long subjectId);

    List<Question> findAllBySubjectIn(List<Subject> subjects);
}
