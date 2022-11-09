package fpt.capstone.vuondau.repository;




import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.FeedBack;
import fpt.capstone.vuondau.entity.PostResourceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack, PostResourceKey> {

    List<FeedBack> countAllByClazz (Class clazz) ;


}
