package com.ime.lockmanager.common.config;

import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.common.jwt.JwtAuthenticationFilter;
import com.ime.lockmanager.common.jwt.JwtHeaderUtil;
import com.ime.lockmanager.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {
    private final JwtHeaderUtil jwtHeaderUtil;
    private final JwtProvider jwtProvider;
    private final AuthToRedisQueryPort authToRedisQueryPort;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/swagger-ui/**", "/h2-console/**", "/index.html");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtHeaderUtil,jwtProvider,authToRedisQueryPort);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/auth/login","/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
