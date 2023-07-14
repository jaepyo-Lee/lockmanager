package com.ime.lockmanager.common.jwt;


import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthToRedisQueryPort authToRedisQueryPort;
    private static String HEADER_AUTHORIZATION = "AccessToken";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = jwtHeaderUtil.getBearerToken(req.getHeader(HEADER_AUTHORIZATION));
        if(bearerToken!=null){
            AuthToken authToken = jwtProvider.convertAuthToken(bearerToken);
            if(authToken.validate() && authToRedisQueryPort.validateToken(bearerToken)){
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(authToken));
            }
        }
        filterChain.doFilter(req, res);
    }
}
