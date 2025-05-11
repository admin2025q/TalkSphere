package com.tt.admin.service.checkservice;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.tt.admin.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DuplicateRequestChecker {

    private final RedisUtil redisUtil;
    private static final String REPEAT_REQUEST_PREFIX = "repeat_request:";
    private static final Duration DEFAULT_LOCK_DURATION = Duration.ofSeconds(5); // 默认锁过期时间

    /**
     * 判断请求是否重复
     *
     * @param userId      用户ID（如果需要基于用户判断）
     * @param path        请求路径
     * @param requestBody 请求体（可以根据实际情况选择关键字段）
     * @return true 如果是重复请求，false 否则
     */
    public boolean isDuplicateRequest(String userId, String path, String requestBody) {
        // 1. 构建唯一标识符
        String uniqueKeySource = userId + ":" + path + ":" + requestBody;
        String uniqueKey = REPEAT_REQUEST_PREFIX + DigestUtils.md5DigestAsHex(uniqueKeySource.getBytes(StandardCharsets.UTF_8));

        // 2. 尝试在 Redis 中设置该标识符，如果设置成功（不存在），则不是重复请求
        Boolean isNew = redisUtil.setIfAbsent(uniqueKey, "locked", DEFAULT_LOCK_DURATION);

        // setIfAbsent 返回 true 如果 key 不存在并成功设置，返回 false 如果 key 已经存在
        return isNew != null && !isNew;
    }

    /**
     * 判断请求是否重复 (不带用户ID)
     *
     * @param path        请求路径
     * @param requestBody 请求体
     * @return true 如果是重复请求，false 否则
     */
    public boolean isDuplicateRequestNotUid(String path, String requestBody) {
        return isDuplicateRequest(null, path, requestBody);
    }

    /**
     * 判断请求是否重复 (只基于用户ID和路径)
     *
     * @param userId 用户ID
     * @param path   请求路径
     * @return true 如果是重复请求，false 否则
     */
    public boolean isDuplicateRequest(String userId, String path) {
        return isDuplicateRequest(userId, path, ""); // 请求体为空
    }

    /**
     * 移除重复请求的标识 (在请求处理完成后调用，如果需要更精确的控制)
     *
     * @param userId      用户ID
     * @param path        请求路径
     * @param requestBody 请求体
     */
    public void clearDuplicateRequest(String userId, String path, String requestBody) {
        String uniqueKeySource = userId + ":" + path + ":" + requestBody;
        String uniqueKey = REPEAT_REQUEST_PREFIX + DigestUtils.md5DigestAsHex(uniqueKeySource.getBytes(StandardCharsets.UTF_8));
        redisUtil.delete(uniqueKey);
    }

    /**
     * 设置自定义的锁过期时间
     *
     * @param duration 锁过期时间
     */
    // public void setLockDuration(Duration duration) {
    //     DEFAULT_LOCK_DURATION = duration;
    // }
}
