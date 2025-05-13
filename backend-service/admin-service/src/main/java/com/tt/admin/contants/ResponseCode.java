package com.tt.admin.contants;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(200, "操作成功"),
    ERROR(500, "系统内部错误"),
    VALIDATION_ERROR(400, "参数校验失败"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),

    // 200 表示 HTTP 成功，101 表示业务错误
    CAPTCHA_KEY_MISSING(200101, "验证码key不能为空"),
    LOGIN_FAILED(200101, "登录失败"),
    CAPTCHA_ERROR(200101, "验证码错误或者过期"),
    USER_NOT_FOUND(200102, "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(200103, "用户名或密码错误");

    // 6 位整数响应码
    private final Integer code;
    // 描述信息
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
