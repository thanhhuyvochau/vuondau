package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.TeachingConfirmation;
import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@Repository
public interface TeachingConfirmationRepository extends JpaRepository<TeachingConfirmation, Long> {
    TeachingConfirmation findByCode(String code);

    @Query(nativeQuery = true, value = "SELECT * from ")
    List<TeachingConfirmation> findByDate(Instant date);
}
