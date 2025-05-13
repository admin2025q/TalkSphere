package com.tt.admin.controller;

import com.tt.admin.contants.ResponseCode;
import com.tt.admin.entity.dto.LoginRequestDTO;
import com.tt.admin.entity.dto.LoginResultDTO;
import com.tt.admin.service.checkservice.LoginService;
import com.tt.admin.util.CaptchaUtil;
import com.tt.vo.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
@RequestMapping("/login")
public class LoginController {
    private static final String CK = "_ck";

    private final LoginService loginService;

    @PostMapping("/submit")
    public ApiResponse<LoginResultDTO> login(
            HttpServletRequest request,
            @Validated @RequestBody LoginRequestDTO requestDto) throws IOException {
        LoginResultDTO resultDTO = new LoginResultDTO();
        final String clientCk = request.getHeader(CK);
        if (StringUtils.isBlank(clientCk)) {
            return ApiResponse.error(ResponseCode.CAPTCHA_KEY_MISSING,resultDTO);
        }
        requestDto.setCk(clientCk);
        return loginService.login(requestDto);
    }

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
        response.setHeader(CK, loginService.setAnonyUser(captcha));
    }



}