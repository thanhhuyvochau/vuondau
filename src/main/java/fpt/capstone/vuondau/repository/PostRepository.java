package fpt.capstone.vuondau.repository;



import fpt.capstone.vuondau.entity.Post;
import fpt.capstone.vuondau.entity.common.EPageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
        List<Post> findPostByTypeAndIsVisibleIsTrue(EPageContent type) ;


}
