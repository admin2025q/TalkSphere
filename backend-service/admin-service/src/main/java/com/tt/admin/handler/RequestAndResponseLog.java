package com.tt.admin.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tt.admin.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestAndResponseLog {
  
    // 请求路径
    private String url;
    // 客户端真实ip
    private String ip;
    // HTTP 方法
    private String method;
    // 请求参数
    private Map<String, String[]> params;
    // 请求头
    private Map<String, String> headers;
    // 请求 Body（截断后）
    private String reqBody;
    ///响应
    private String repBody;
    // 响应状态码
    private Integer status;

    // 转换为 JSON 字符串（Logstash 兼容） 使用 Jackson/Gson
    public String toString() {
        return JsonUtil.toJson(this);
    }
}