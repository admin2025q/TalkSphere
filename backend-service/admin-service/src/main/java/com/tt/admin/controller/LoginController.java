package com.tt.admin.controller;

import com.tt.admin.service.adminuser.cache.AdminUserRedisKey;
import com.tt.admin.util.Base64Util;
import com.tt.admin.util.CaptchaUtil;
import com.tt.admin.util.RedisUtil;
import com.tt.vo.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 登录控制器
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class LoginController {

    private static final String _CK = "_ck";

    private final RedisUtil redisUtil;

    @GetMapping("/thread-info")
    public ApiResponse<String> getThreadInfo() {
        Thread thread = Thread.currentThread();
        String format = String.format("Thread ID: %d, Name: %s, Virtual: %b",
                thread.threadId(), thread.getName(), thread.isVirtual());
        return ApiResponse.success(format);
    }

    /**
     * 获取图形验证码
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        // 设置响应类型为图片
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        UUID uuid = UUID.randomUUID();
        String encode = Base64Util.encode(uuid.toString());
        response.setHeader(_CK, encode);
        // 调用工具类生成验证码
        String captcha = CaptchaUtil.generateCaptcha(response.getOutputStream());
        log.info("Generated captcha: {}", captcha);
        redisUtil.setExpire(AdminUserRedisKey.getCaptchaKey(encode), captcha,60*1000);
    }

    @PostMapping("/login")
    public void login(@RequestBody String body) throws IOException {
        log.info("Login request body: {}", body);
    }

}