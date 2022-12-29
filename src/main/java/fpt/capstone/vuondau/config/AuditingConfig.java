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

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(SecurityUtil.getCurrentUserName().orElse(AuthoritiesConstants.ANONYMOUS));
    }
}