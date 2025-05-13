package com.tt.admin.handler.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tt.admin.entity.dao.MenuPermission;
import com.tt.admin.service.AdminMenuPermissionService;
import com.tt.admin.service.adminuser.AdminPermissionService;
import com.tt.admin.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminPermissionService adminPermissionService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<MenuPermission> selectAllPermission = adminPermissionService.selectAllPermission();
        log.info("============ {}",JsonUtil.toJson(selectAllPermission));
        http.csrf(csrf -> csrf.disable()) // 禁用 CSRF 保护
                .authorizeHttpRequests(authorize -> {
                    for (MenuPermission mp : selectAllPermission) {
                        authorize.requestMatchers(mp.getPath()).hasAuthority(mp.getCode());
                    }
                    authorize
                            // 允许访问 /.well-known/ 下的所有请求
                            .requestMatchers("/.well-known/**").permitAll()
                            // 登录接口允许匿名访问
                            .requestMatchers("/login/*").permitAll()
                            // 仅管理员可访问
                            // .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated();// 其他请求需要认证
                })
                // 禁用默认登录页面
                .formLogin(form -> form.disable());
        return http.build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    //         throws Exception {
    //     return authenticationConfiguration.getAuthenticationManager();
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder(); // 使用 BCrypt 加密
    // }
}