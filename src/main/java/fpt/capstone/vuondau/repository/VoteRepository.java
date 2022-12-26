package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByQuestion_Id(Long questionId);

    List<Vote> findAllByComment_Id(Long commentId);
}
