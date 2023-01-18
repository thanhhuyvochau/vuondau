package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.FeedbackAccountLog;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import fpt.capstone.vuondau.entity.response.FeedbackAccountLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackAccountLogRepository extends JpaRepository<FeedbackAccountLog, Long> {

    List<FeedbackAccountLog> findFeedbackAccountLogByAccountDetailId(Long accountDetail);


}
