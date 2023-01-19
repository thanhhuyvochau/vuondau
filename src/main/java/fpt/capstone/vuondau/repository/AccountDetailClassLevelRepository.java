package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountDetailClassLevelRepository extends JpaRepository<AccountDetailClassLevel, Long> {
    AccountDetailClassLevel findByAccountDetailAndClassLevel(AccountDetail accountDetail, ClassLevel classLevel);

    List<AccountDetailClassLevel> findAllByAccountDetail(AccountDetail accountDetail);
}
