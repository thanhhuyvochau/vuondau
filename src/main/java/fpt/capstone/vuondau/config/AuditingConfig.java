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
        return () -> Optional.of(Optional.ofNullable(getAuthenticationUserName()).orElse(AuthoritiesConstants.ANONYMOUS));
    }

    private String getAuthenticationUserName() {
        return securityUtil.getCurrentUserName();
    }
}