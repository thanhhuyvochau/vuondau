package fpt.capstone.vuondau.repository;



import fpt.capstone.vuondau.entity.RequestReply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RequestReplyRepository extends JpaRepository<RequestReply, Long> {



}
