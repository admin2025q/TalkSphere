package com.tt.admin.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tt.admin.util.CaptchaUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 登录控制器
 * </p>
 * @author tt
 * @since 2025-05-10
 */
 @RestController
 @RequestMapping("/admin/login")
 @Slf4j
public class LoginController {

    /**
     * 获取图形验证码
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        // 设置响应类型为图片
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        // 调用工具类生成验证码
        String captcha = CaptchaUtil.generateCaptcha(response.getOutputStream());
        // 将验证码存储到 session 或其他存储中（如 Redis）
        // 示例：存储到 session
        // response.getSession().setAttribute("captcha", captcha);
        log.info("Generated captcha: {}", captcha);
    }
}