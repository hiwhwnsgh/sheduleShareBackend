package com.scheduleManagement.schedule.config;

import com.scheduleManagement.schedule.dto.UserRole;
import com.scheduleManagement.schedule.jwt.JwtTokenFilter;
import com.scheduleManagement.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private static String secretKey = "o/3KOExETnCvjVQytqlpli95oOSqkDKvjoT3isLB50D/1MAl4tBWSIUFK9nGKi0qu6Fsw4+a1YmiTn5aW0jo+g==";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/info").authenticated()
                .antMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())
                .and().build();
    }
}