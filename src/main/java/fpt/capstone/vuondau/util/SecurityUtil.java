package fpt.capstone.vuondau.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.moodle.response.MoodleUserResponse;
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
    private final MoodleUtil moodleUtil;

    public SecurityUtil(AccountRepository accountRepository, MoodleUtil moodleUtil) {
        this.accountRepository = accountRepository;
        this.moodleUtil = moodleUtil;
    }

    public Account getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt principal = (Jwt) authentication.getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        Account account = Optional.ofNullable(accountRepository.findByUsername(username))
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Student not found by username"));
        synchronizedAccountInfo(principal, account);
        return account;
    }

    private void synchronizedAccountInfo(Jwt principal, Account account) {
        try {
            if (account.getKeycloakUserId() == null) {
                account.setKeycloakUserId(principal.getClaimAsString("sub"));
            }
            if (account.getMoodleUserId() == null) {
                MoodleUserResponse moodleUserResponse = moodleUtil.getMoodleUserIfExistByKeycloakId(account.getKeycloakUserId());
                account.setMoodleUserId(moodleUserResponse.getId());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static Optional<String> getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        Jwt principal = (Jwt) authentication.getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        return Optional.ofNullable(username);
    }

    public static Jwt getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw ApiException.create(HttpStatus.CONFLICT).withMessage("User principal is null!");
        }
        return (Jwt) authentication.getPrincipal();
    }
}
