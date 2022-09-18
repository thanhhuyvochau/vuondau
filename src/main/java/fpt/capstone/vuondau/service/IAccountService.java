package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findByUsername(String username);

    Long saveAccount(Account account);
}
