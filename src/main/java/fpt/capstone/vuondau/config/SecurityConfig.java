package fpt.capstone.vuondau.config;

import fpt.capstone.vuondau.config.security.JwtGrantedAuthoritiesConverterCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        super.configure(http);
//        http
//                .authorizeRequests()
//                .antMatchers("/admin/*").hasRole("admin")
//                .antMatchers("/api/teachers/*").hasRole("teacher")
//                .antMatchers("api/teachers").hasRole("admin")
//                .anyRequest().permitAll();
//        http
//                .csrf().disable();
//                .authorizeRequests().anyRequest().permitAll();
//        http
//                .csrf().disable()    //Disabling CSRF as not using form based login

//        http.cors()
//                .and()

//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

        http.cors()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();
    }



    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverterCustom grantedAuthoritiesConverterCustom = new JwtGrantedAuthoritiesConverterCustom();
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverterCustom);
        return jwtAuthenticationConverter;
    }
}