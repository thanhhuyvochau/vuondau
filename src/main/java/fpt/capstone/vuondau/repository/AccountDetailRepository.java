package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.Account;
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

    List<AccountDetail> findAllByIdInAndIsActiveIsFalse(List<Long> ids);

    //   Optional<AccountDetail> findAllByIdsInAndIsActiveIsFalse(long id);
    Page<AccountDetail> findAllByIsActiveIsFalse(Pageable pageable);

    Boolean existsAccountByEmail(String email);

    Boolean existsAccountDetailByIdCard(String idCart);

    Boolean existsAccountByPhone(String phone);

    AccountDetail findByAccount(Account account);

    Page<AccountDetail> findAllByIsActive(Boolean isActive, Pageable pageable);

}
