package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.common.EAccountRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
//    Optional<Account> findByUsername(String username);

    List<Account> findAccountByRole (EAccountRole eAccountRole) ;


    Page<Account> findAll(Specification<Account> spec, Pageable pageable);
}
