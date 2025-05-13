package com.tt.admin.handler.async;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author zt
 */
public class VirtualThreadConverter  extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        Thread thread = Thread.currentThread();
        if (thread.isVirtual()) {
            // 示例: VT-123
            return "VT-" + thread.threadId();
        }
        // 普通线程仍用原名
        return thread.getName();
    }
}
