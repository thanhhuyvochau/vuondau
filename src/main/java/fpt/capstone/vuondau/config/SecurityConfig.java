package fpt.capstone.vuondau.config;

import fpt.capstone.vuondau.config.security.SecurityFilter;
import fpt.capstone.vuondau.config.security.UnAuthorizedUserAuthenticationEntryPoint;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
@EnableWebSecurity
@Import(KeycloakSpringBootConfigResolver.class)
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;


    private final BCryptPasswordEncoder bCryptEncoder;


    private final UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;


    private final SecurityFilter secFilter;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptEncoder, UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint, SecurityFilter secFilter) {
        this.userDetailsService = userDetailsService;
        this.bCryptEncoder = bCryptEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.secFilter = secFilter;
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptEncoder);
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager()throws Exception {
        return super.authenticationManager() ;
    }

    @Bean
    protected SessionRegistry buildSessionRegistry(){
        return  new SessionRegistryImpl() ;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean() ;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("admin")
                .antMatchers("/user/*").hasRole("teacher")
                .anyRequest().permitAll();
        http
                .csrf().disable();
//                .authorizeRequests().anyRequest().permitAll();
//        http
//                .csrf().disable()    //Disabling CSRF as not using form based login
//                .authorizeRequests()
//                .antMatchers("/user/saveUser","/user/loginUser").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                //To Verify user from second request onwards............
//                .and()
//                .addFilterBefore(secFilter, UsernamePasswordAuthenticationFilter.class)
//        ;
    }
}