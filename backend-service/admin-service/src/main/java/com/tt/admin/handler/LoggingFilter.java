package com.tt.admin.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.tt.admin.service.checkservice.LoginService;
import com.tt.admin.util.IPUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * // TODO敏感信息过滤（如密码、token）
 * // TODO异步日志处理（使用AsyncAppender）
 * 请求唯一ID跟踪
 * // TODO慢请求阈值警告
 * // TODO日志采样配置（生产环境）
 *
 * @author zz
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    // 需要排除的路径
    private static final String[] EXCLUDE_PATH = {
            "/.well-known/",
            "/actuator/health",
            "/favicon.ico",
            "/static/",
            ".js"
    };

    private static final String[] EXCLUDE_CONTENTTYPE = {
            "image/png"
    };
    @Value("${spring.profiles.active:default}") 
    private String evnactive;
    
    // 10KB
    private static final int MAX_BODY_LENGTH = 1024 * 10;
    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String MDC_TRACE_KEY = "traceId";
   
    private final LoginService loginService;
    @Override
    public final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 优先从Header获取TraceID
        String traceId = request.getHeader(TRACE_ID_HEADER);
        String ipAddress = IPUtils.getClientRealIP(request);
        boolean isNotDevEnv = !StringUtils.equalsIgnoreCase(evnactive, "dev");
        if (isNotDevEnv && StringUtils.isBlank(traceId)) {
            return;
        }
        //TODO 请求重复校验

        MDC.put(MDC_TRACE_KEY, traceId);
        try {
            // 包装请求和响应以缓存 Body
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            // 记录请求信息（虚拟线程中异步执行）
            // Thread.startVirtualThread(() -> logRequest(wrappedRequest));
            logRequest(wrappedRequest,ipAddress);
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
            // 继续执行后续 Filter 和 Controller
            chain.doFilter(wrappedRequest, wrappedResponse);
            // 记录响应信息
            logResponse(wrappedResponse);

            // 确保响应数据写回客户端
            wrappedResponse.copyBodyToResponse();
        } finally {
            MDC.remove(MDC_TRACE_KEY);
        }

    }

    private void logRequest(ContentCachingRequestWrapper request,String clientRealIP) {
        Map<String, String> headers = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, request::getHeader));
        String body = getTruncatedBody(request.getContentAsByteArray(), request.getCharacterEncoding());
        LogEvent logEvent = LogEvent.builder()
                .type("REQUEST")
                .url(request.getRequestURI())
                .ip(clientRealIP)
                .method(request.getMethod())
                .params(request.getParameterMap())
                .headers(headers)
                .body(body)
                .build();

        log.info(logEvent.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        val builder = LogEvent.builder();
        String body = getTruncatedBody(response.getContentAsByteArray(), response.getCharacterEncoding());
        for (String excludePath : EXCLUDE_CONTENTTYPE) {
            if (excludePath.equalsIgnoreCase(response.getContentType())) {
                builder.body(excludePath);
            } else {
                builder.body(body);
            }
        }
        val logEvent = builder.type("RESPONSE")
                .status(response.getStatus())
                .build();
        log.info(logEvent.toString());
    }

    private String getTruncatedBody(byte[] bodyBytes, String encoding) {
        if (bodyBytes == null || bodyBytes.length == 0) {
            return "";
        }
        String body = new String(bodyBytes, StandardCharsets.UTF_8);
        return body.length() > MAX_BODY_LENGTH ? body.substring(0, MAX_BODY_LENGTH) + "...[TRUNCATED]" : body;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String excludePath : EXCLUDE_PATH) {
            if (path.startsWith(excludePath)) {
                return true;
            }
        }
        return false;
    }

}
