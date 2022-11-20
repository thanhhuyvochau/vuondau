package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    Class findByCourseAndAccount(Course course , Account account );

   Optional<Class> findByIdAndCourse(long course , Course account );

//    List<Class> findClassByAccount (Account account );
}
