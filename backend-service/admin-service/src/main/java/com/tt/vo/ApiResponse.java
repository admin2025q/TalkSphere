package com.tt.vo;

import com.tt.admin.contants.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    // 状态码
    private int code;
    // 返回信息
    private String message;
    // 返回数据
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    public static <T> ApiResponse<T> error(ResponseCode responseCode,T data) {
        return new ApiResponse<>(responseCode.getCode(), responseCode.getMessage(), data);
    }
}
