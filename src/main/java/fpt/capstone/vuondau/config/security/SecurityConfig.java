package fpt.capstone.vuondau.config.security;

import fpt.capstone.vuondau.config.security.converter.JwtGrantedAuthoritiesConverterCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;


//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**Config for development */
        http.cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()
                .authorizeRequests().anyRequest().permitAll();

        /**Config for deployment */
//        http.cors().configurationSource(corsConfigurationSource())
//                .and().csrf().disable()
//                .authorizeRequests().antMatchers("/api/*").authenticated()
//                .and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
//                .and().authenticationEntryPoint(new BasicAuthenticationEntryPoint());
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverterCustom grantedAuthoritiesConverterCustom = new JwtGrantedAuthoritiesConverterCustom();
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverterCustom);
        return jwtAuthenticationConverter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}