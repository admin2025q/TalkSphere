package com.tt.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // 设置值
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    // 获取值
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除值
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 判断key是否存在 
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 设置过期时间
    public void setExpire(String key, Object value,long timeout) {
        redisTemplate.opsForValue().set(key,value, timeout);
    }
}
