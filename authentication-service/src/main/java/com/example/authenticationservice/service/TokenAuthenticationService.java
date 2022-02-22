package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AuthenticationTokenImpl;
import com.example.authenticationservice.domain.SessionUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Sha512DigestUtils;

public class TokenAuthenticationService {

    private final RedisService service;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hr
    private final String secret;
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER = "Authorization";

    public TokenAuthenticationService(RedisService service, String key) {
        this.service = service;
        secret = Sha512DigestUtils.shaHex(key);
    }

    public void addAuthentication(HttpServletResponse response, AuthenticationTokenImpl auth) {
        //Create key-value pair claims in Redis
        // username -> hash(password)
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", auth.getPrincipal());
        claims.put("hash", auth.getHash());
        // Generate the token
        String JWT = Jwts.builder()
                .setSubject(auth.getPrincipal().toString())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        response.addHeader(HEADER, TOKEN_PREFIX + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (token == null) {
            return null;
        }
        //remove "Bearer" text
        token = token.replace(TOKEN_PREFIX, "").trim();

        //Validating the token
        if (token != null && !token.isEmpty()) {
            // parsing the token.`
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token).getBody();

            } catch ( Exception e) {
                return null;
            }

            //Check if the token has been expired by generating a redis query.
            if (claims != null && claims.containsKey("username")) {
                String username = claims.get("username").toString();
                String hash = claims.get("hash").toString();
                SessionUser user = (SessionUser) service.getValue(String.format("%s:%s", username,hash), SessionUser.class);
                if (user != null) {
                    AuthenticationTokenImpl auth = new AuthenticationTokenImpl(user.getUsername(), Collections.emptyList());
                    auth.setDetails(user);
                    auth.authenticate();
                    return auth;
                } else {
                    return new UsernamePasswordAuthenticationToken(null, null);
                }

            }
        }
        return null;
    }
}
