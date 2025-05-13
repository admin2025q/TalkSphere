package com.tt.admin.handler;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.tt.admin.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tt.vo.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zrt
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ApiResponse<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    // 统一处理所有校验异常
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ApiResponse<String> handleIllegalArgumentException(Exception ex) {
        final BindingResult bindingResult = resolveBindingResult(ex);

        if (bindingResult == null || !bindingResult.hasErrors()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败无效的请求参数");
        }
        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .toList();
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), JsonUtil.toJson(errors));
    }
    // 可以添加更多 @ExceptionHandler 方法来处理不同类型的异常
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<String> handleGenericException(Exception ex) {
        // 记录更详细的错误日志，方便排查问题
        log.error("服务器内部错误请稍后重试或联系管理员",ex);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    private BindingResult resolveBindingResult(Exception ex) {
        return switch(ex) {
            case MethodArgumentNotValidException manve -> manve.getBindingResult();
            case BindException bindEx -> bindEx;
            default -> null;
        };
    }

}
