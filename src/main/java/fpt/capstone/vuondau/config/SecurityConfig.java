package fpt.capstone.vuondau.config;

import fpt.capstone.vuondau.config.security.SecurityFilter;
import fpt.capstone.vuondau.config.security.UnAuthorizedUserAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Autowired
    private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecurityFilter secFilter;

    //Required in case of Stateless Authentication
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

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

        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }
}