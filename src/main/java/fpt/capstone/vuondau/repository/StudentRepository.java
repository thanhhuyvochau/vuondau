package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where lower(s.firstName) like lower(concat('%',:name,'%')) or lower(s.lastName) like lower(concat('%',:name,'%'))")
    List<Student> findAllByFirstNameLikeOrLastNameLike(String name);

    List<Student> findAllByAccount_IsActive(Boolean active);
}
