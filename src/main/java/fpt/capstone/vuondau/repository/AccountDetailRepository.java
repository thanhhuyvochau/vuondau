package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.AccountDetail;

import fpt.capstone.vuondau.entity.common.ApiPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

   Page<AccountDetail> findAllByIsActiveIsTrue(Pageable pageable);
   Optional<AccountDetail> findByIdAndIsActiveIsFalse(long id);
   Page<AccountDetail> findAllByIsActiveIsFalse(Pageable pageable);

   Boolean existsAccountByEmail (String email) ;

   Boolean existsAccountByPhone(String phone) ;
}
