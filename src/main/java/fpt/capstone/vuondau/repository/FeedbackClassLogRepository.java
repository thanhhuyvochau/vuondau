package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.FeedbackAccountLog;
import fpt.capstone.vuondau.entity.FeedbackClassLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackClassLogRepository extends JpaRepository<FeedbackClassLog, Long> {




}
