package com.tt.admin.service.adminuser.cache;

public class AdminUserRedisKey {

    private static final String ADMIN_USER = "ADMIN_USERCK:";

    public static String getCaptchaKey(String username) {
        return ADMIN_USER + username;
    }
}
