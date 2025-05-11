package com.tt.admin.handler;

import java.util.UUID;

public class RequestIdHolder {

    private static final ThreadLocal<String> requestId = new ThreadLocal<>();

    public static String getRequestId() {
        String id = requestId.get();
        if (id == null) {
            id = UUID.randomUUID().toString();
            requestId.set(id);
        }
        return id;
    }

    public static void clear() {
        requestId.remove();
    }
}
