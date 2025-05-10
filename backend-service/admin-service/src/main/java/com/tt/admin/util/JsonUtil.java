package com.tt.admin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 基础配置优化
        objectMapper
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 禁用时间戳格式
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // 忽略未知字段
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS) // 枚举不区分大小写
            .registerModule(new JavaTimeModule()) // 支持 LocalDateTime 等时间类型
            .registerModule(new Jdk8Module()); // 支持 JDK8 Optional 等新特性

        // JDK21 记录类支持（Jackson 2.16+ 原生适配）
        objectMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true);
        objectMapper.configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, true);
    }

    /**
     * 对象 → JSON（支持记录类）
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException("对象转 JSON 失败", e);
        }
    }

    /**
     * JSON → 对象（支持泛型）
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON 转对象失败", e);
        }
    }

    /**
     * JSON → 复杂泛型对象（如 List<T>、Map<K,V>）
     */
    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON 转复杂对象失败", e);
        }
    }

    /**
     * JSON → 集合类型（如 List<User>）
     */
    public static <T> List<T> parseList(String json, Class<T> elementType) {
        try {
            return objectMapper.readValue(json, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON 转集合失败", e);
        }
    }

    /**
     * JSON → Map 类型（如 Map<String, User>）
     */
    public static <K, V> Map<K, V> parseMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON 转 Map 失败", e);
        }
    }

    // 自定义异常类（避免 checked exception）
    public static class JsonException extends RuntimeException {
        public JsonException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
