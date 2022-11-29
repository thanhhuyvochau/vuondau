package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
}
