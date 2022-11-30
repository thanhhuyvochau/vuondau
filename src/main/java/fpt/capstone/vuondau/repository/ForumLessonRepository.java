package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Forum;
import fpt.capstone.vuondau.entity.ForumLesson;
import fpt.capstone.vuondau.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumLessonRepository extends JpaRepository<ForumLesson, Long> {
}
