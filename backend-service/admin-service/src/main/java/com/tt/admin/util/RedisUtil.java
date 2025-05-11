package com.tt.admin.util;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    // 直接使用
    private final StringRedisTemplate stringRedisTemplate;

    // 设置值
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // 获取值
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 删除值
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    // 判断key是否存在 
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    // 设置过期时间
    public void setExpire(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }

    // 设置过期时间
    public boolean setIfAbsent(String key, String value, Duration timeout) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout);
    }
}
