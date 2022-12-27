package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByQuestion_IdAndParentCommentIsNull(Long questionId);
}
