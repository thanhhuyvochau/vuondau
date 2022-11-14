package fpt.capstone.vuondau.config.security.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class JwtGrantedAuthoritiesConverterCustom implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(jwt)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        String claimName = "resource_access";
        Map<String, Object> resourceAccess = jwt.getClaimAsMap(claimName);
        Map<String, Object> employeeService = (Map<String, Object>) resourceAccess.get("employee-service");
        Collection<String> roles = (Collection<String>) employeeService.get("roles");
        if (!roles.isEmpty()) {
            return roles;
        }
        return Collections.emptyList();
    }
}
