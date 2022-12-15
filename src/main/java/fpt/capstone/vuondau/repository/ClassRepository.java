package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    Class findByCode(String code);

    Class findByCourseAndAccount(Course course, Account account);

    List<Class> findByAccountAndStatus(Account account, EClassStatus status);

    Page<Class> findAllByAccount(Account account, Pageable pageable);

    Optional<Class> findByIdAndCourse(long course, Course account);

    //    List<Class> findAll(Specification<Class> spec, Pageable pageable);
    Page<Class> findAllByIsActiveIsTrue(Pageable pageable);

//    Page<Class> findAllByActiveIsTrue(Pageable pageable);

    List<Class> findAll(Specification<Class> spec);

    Page<Class> findAll(Specification<Class> spec, Pageable pageable);

    boolean existsByCode(String code);

    List<Class> findByIdInAndStatus(List<Long> classId, EClassStatus status);

//    List<Class> findClassByAccount (Account account );

    Page<Class> findAllByStatus(EClassStatus eClassStatus, Pageable pageable);
}
