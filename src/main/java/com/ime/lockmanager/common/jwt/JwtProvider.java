package com.ime.lockmanager.common.jwt;

import com.ime.lockmanager.auth.domain.AuthUser;
import com.ime.lockmanager.common.format.exception.auth.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    private static Key SECRET_KEY;
    private static Long ACCESS_TOKEN_EXPIRATION_MILLISECONDS;
    private static Long REFRESH_TOKEN_EXPIRATION_MILLISECONDS;
    private final String BEARER = "Bearer";

    private final String USER_NAME = "userName";
    private final String STUDENT_NUM = "studentNum";
    private final String ROLE = "role";

    public JwtProvider(
            @Value("${jwt.secret-key}") String SECRET_KEY,
            @Value("${jwt.access-token-expiration}") Long ACCESS_TOKEN_EXPIRATION_MILLISECONDS,
            @Value("${jwt.refresh-token-expiration}") Long REFRESH_TOKEN_EXPIRATION_MILLISECONDS
    ) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        this.ACCESS_TOKEN_EXPIRATION_MILLISECONDS = ACCESS_TOKEN_EXPIRATION_MILLISECONDS;
        this.REFRESH_TOKEN_EXPIRATION_MILLISECONDS = REFRESH_TOKEN_EXPIRATION_MILLISECONDS;
        System.out.println(this.ACCESS_TOKEN_EXPIRATION_MILLISECONDS);
        System.out.println(this.REFRESH_TOKEN_EXPIRATION_MILLISECONDS);

    }

    public String createAccessToken(AuthUser user){
        return createAccessToken(user,ACCESS_TOKEN_EXPIRATION_MILLISECONDS);
    }
    public String createRefreshToken(AuthUser user){
        return createRefreshToken(user,REFRESH_TOKEN_EXPIRATION_MILLISECONDS);
    }
    public String createAccessToken(AuthUser user, Long time){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + time);
        return Jwts.builder()
                .setSubject(user.getUser().getName())
                .setClaims(createClaimByAuthUser(user))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }
    public String createRefreshToken(AuthUser user, Long time){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + time);
        return Jwts.builder()
                .setSubject(user.getUser().getName())
                .setClaims(createClaimByAuthUser(user))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    public Long getTokenExpiration(String token){
        Claims body = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body.getExpiration().getTime();
    }

    private Map<String, Object> createClaimByAuthUser(AuthUser user){
        Map<String, Object> map = new HashMap<>();
        map.put(USER_NAME, user.getUser().getName());
        map.put(STUDENT_NUM, user.getUser().getStudentNum());
        map.put(ROLE, user.getRole());
        return map;
    }

    public AuthToken convertAuthToken(String bearerToken) {
        return AuthToken.of(bearerToken,SECRET_KEY);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validate()) {
            Claims tokenClaims = authToken.getTokenClaims();
            log.debug("{}",tokenClaims.get("userName"));

            log.debug("{}",tokenClaims.get("studentNum"));
            log.info("{}",tokenClaims.get("role"));
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(new String[]{tokenClaims.get("role").toString()})
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            System.out.println(authorities);
            User principal = new User((String) tokenClaims.get("studentNum"), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal,null ,authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}
