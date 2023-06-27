package com.ime.lockmanager.common.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHeaderUtil jwtHeaderUtil;
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = jwtHeaderUtil.getBearerToken(req);
        if(bearerToken!=null){
            AuthToken authToken = jwtProvider.convertAuthToken(bearerToken);
            if(authToken.validate()){
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(authToken));
            }
        }
    }
}
