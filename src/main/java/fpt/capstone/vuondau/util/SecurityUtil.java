package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    private final AccountRepository accountRepository;

    public SecurityUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt principal = (Jwt) authentication.getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        return Optional.ofNullable(accountRepository.findByUsername(username))
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Student not found by username"));
    }

    public Account getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new Account();
        }
        Jwt principal = (Jwt) authentication.getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        return Optional.ofNullable(accountRepository.findByUsername(username))
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Student not found by username"));
    }
}
