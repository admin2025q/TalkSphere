package com.tt.admin.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tt.vo.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ApiResponse<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("参数不合法",ex.getMessage());
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    // 可以添加更多 @ExceptionHandler 方法来处理不同类型的异常
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<String> handleGenericException(Exception ex) {
        // 记录更详细的错误日志，方便排查问题
        log.error("服务器内部错误", "请稍后重试或联系管理员",ex);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
