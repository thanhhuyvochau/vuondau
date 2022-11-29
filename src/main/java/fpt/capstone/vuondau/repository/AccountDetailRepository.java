package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.AccountDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {



}
