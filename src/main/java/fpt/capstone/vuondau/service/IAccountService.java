package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findByUsername(String username);

    Long saveAccount(Account account);

    AccountTeacherResponse createTeacherAccount(AccountExistedTeacherRequest accountRequest);
}
