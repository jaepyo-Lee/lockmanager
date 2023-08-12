package com.ime.lockmanager.common.jwt;

import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtHeaderUtil {
    private static String TOKEN_PREFIX = "Bearer ";
    public String getBearerToken(String token){
        if(token==null){
            return null;
        }
        String accessToken = token.substring(TOKEN_PREFIX.length());
        if(accessToken==null){
            return null;
        }
        return accessToken;
    }

}
