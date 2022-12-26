package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Comment;
import fpt.capstone.vuondau.entity.Question;
import fpt.capstone.vuondau.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByQuestion_Id(Long questionId);

    List<Vote> findAllByComment_Id(Long commentId);

    Optional<Vote> findByCommentAndAccount(Comment comment, Account account);

    Optional<Vote> findByQuestionAndAccount(Question question, Account account);
}
