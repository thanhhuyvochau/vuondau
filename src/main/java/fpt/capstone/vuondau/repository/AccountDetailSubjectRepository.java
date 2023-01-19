package fpt.capstone.vuondau.repository;


import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.AccountDetailClassLevel;
import fpt.capstone.vuondau.entity.AccountDetailSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailSubjectRepository extends JpaRepository<AccountDetailSubject, Long> {
    List<AccountDetailSubject> findAllByAccountDetail(AccountDetail accountDetail);


}
