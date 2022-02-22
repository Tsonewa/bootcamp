package com.example.authenticationservice.domain;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.DigestUtils;

@Slf4j
@ToString(callSuper = true)
public class AuthenticationTokenImpl extends AbstractAuthenticationToken{

    @Setter
    private String username;

    public AuthenticationTokenImpl(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = principal;
    }

    //Validate the input data
    public void authenticate() {
        setAuthenticated(getDetails() != null && getDetails() instanceof SessionUser && !((SessionUser) getDetails()).hasExpired());
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return username != null ? username.toString() : "";
    }

    public String getHash() {
        return DigestUtils.md5DigestAsHex(String.format("%s_%d", username, ((SessionUser) getDetails()).getCreated().getTime()).getBytes());
    }

}
