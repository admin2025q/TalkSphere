package com.tt.admin.util;

import jakarta.servlet.http.HttpServletRequest;

public class IPUtils {
public static String getClientRealIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 如果通过 X-Forwarded-For 获取到多个 IP，通常第一个是非代理服务器的 IP，即客户端的真实 IP
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(",")).trim();
        }

        return ipAddress;
    }
}
