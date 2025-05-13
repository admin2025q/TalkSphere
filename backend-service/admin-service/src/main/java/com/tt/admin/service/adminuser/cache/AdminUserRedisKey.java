package com.tt.admin.service.adminuser.cache;

/**
 * @author 99
 */
public class AdminUserRedisKey {

    private static final String ADMIN_USER = "ADMIN_CAPTCHA:";

    public static String getCaptchaKey(String username) {
        return ADMIN_USER + username;
    }
}
