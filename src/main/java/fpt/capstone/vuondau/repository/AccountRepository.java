package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Optional<Account> findByUsername(String username);

    Page<Account> findAccountByRole(Pageable pageable, Role role);


    Page<Account> findAccountByRoleIn(Pageable pageable, List<Role> role);
    Page<Account> findAccountByRoleAndIsActiveIsFalse(Pageable pageable, Role role);

    Page<Account> findAccountByRoleAndAccountDetailStatus(Role role, EAccountDetailStatus detailStatus ,Pageable pageable);

    List<Account> findAllByIdInAndIsActiveIsFalse(List<Long> ids);
    List<Account> findAllByIdIn(List<Long> ids);
    Page<Account> findAll(Specification<Account> spec, Pageable pageable);

    Boolean existsAccountByUsername(String username);


//    Boolean existsAccountByEmail(String email);

    Account findByUsername(String username);
    List<Account> findAllByMoodleUserIdIn(List<Integer> ids);



}
