package fpt.capstone.vuondau.repository;



import fpt.capstone.vuondau.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
