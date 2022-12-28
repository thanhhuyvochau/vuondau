package fpt.capstone.vuondau.config;

import fpt.capstone.vuondau.config.security.AuthoritiesConstants;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {
    private final SecurityUtil securityUtil;

    public AuditingConfig(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        Account currentUser = securityUtil.getCurrentUserName();
        String username = Optional.ofNullable(currentUser.getUsername()).orElse(AuthoritiesConstants.ANONYMOUS);
        return () -> Optional.of(username);
    }
}