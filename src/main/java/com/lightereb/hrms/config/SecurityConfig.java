package com.lightereb.hrms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        // 关键：对Druid路径禁用CSRF保护
                        .ignoringRequestMatchers("/druid/**")
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/druid/**").permitAll()
                        // 其他路径配置...
                );

        return http.build();
    }
}