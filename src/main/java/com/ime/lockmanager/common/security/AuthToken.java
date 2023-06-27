package com.ime.lockmanager.common.security;

import com.ime.lockmanager.auth.domain.AuthUser;
import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
@Builder
public class AuthToken {
    private String accessToken;
    private Key key;

    public static AuthToken of(String bearerToken){
        return AuthToken.builder()
                .accessToken(bearerToken)
                .build();
    }

    public boolean validate(){
        return getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }
}
