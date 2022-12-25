package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Forum;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.EForumType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    Optional<Forum> findForumByClazz(Class clazz);

    Optional<Forum> findForumBySubject(Subject subject);

    Page<Forum> findAllByType(EForumType forumType, Pageable pageable);

    Page<Forum> findAllByClazzIn(List<Class> classes);
}
