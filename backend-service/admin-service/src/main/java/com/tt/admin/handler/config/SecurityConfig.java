package com.tt.admin.handler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable()) // 禁用 CSRF 保护
            .authorizeHttpRequests(authorize -> authorize
                // 允许访问 /.well-known/ 下的所有请求
                .requestMatchers("/.well-known/**").permitAll()
                // 登录接口允许匿名访问
                .requestMatchers("/login").permitAll()
                // 仅管理员可访问
                .requestMatchers("/admin/**").hasRole("ADMIN") 
                .anyRequest().authenticated() // 其他请求需要认证
            )
            .formLogin(form->form.disable()); // 禁用默认登录页面
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 BCrypt 加密
    }
}