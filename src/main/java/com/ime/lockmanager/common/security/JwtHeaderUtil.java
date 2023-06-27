package com.ime.lockmanager.common.security;

import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtHeaderUtil {
    private static String HEADER_AUTHORIZATION = "Authorization";
    private static String TOKEN_PREFIX = "Bearer ";
    public String getBearerToken(HttpServletRequest req){
        String header = req.getHeader(HEADER_AUTHORIZATION);
        if(header==null){
            return null;
        }
        String accessToken = header.substring(TOKEN_PREFIX.length());
        if(accessToken==null){
            return null;
        }
        return accessToken;
    }
}
