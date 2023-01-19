package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByAccountDetail(AccountDetail accountDetail);

}
