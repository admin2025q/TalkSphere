package com.tt.admin.service.checkservice.impl;

import java.time.Duration;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tt.admin.contants.ResponseCode;
import com.tt.admin.dto.LoginRequestDTO;
import com.tt.admin.dto.LoginResultDTO;
import com.tt.admin.entity.AdminUser;
import com.tt.admin.mapper.AdminUserMapper;
import com.tt.admin.service.adminuser.cache.AdminUserRedisKey;
import com.tt.admin.service.checkservice.LoginService;
import com.tt.admin.util.Base64Util;
import com.tt.admin.util.RedisUtil;
import com.tt.vo.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisUtil redisUtil;
    private static final Duration DEFAULT_CK_DURATION = Duration.ofMillis(2);

    private final AdminUserMapper adminUserMapper;
    @Override
    public String setAnonyUser(String anonyUid, String captcha) {
        UUID uuid = UUID.randomUUID();
        String encode = Base64Util.encode(uuid.toString());
        String captchaKey = AdminUserRedisKey.getCaptchaKey(encode);
        redisUtil.setExpire(captchaKey, captcha, DEFAULT_CK_DURATION);
        log.info("Generated captchaKey:{},captcha: {}", captchaKey, captcha);
        return encode;
    }

    @Override
    public ApiResponse<LoginResultDTO> login(LoginRequestDTO requestDto) {
        String captchaKey = AdminUserRedisKey.getCaptchaKey(requestDto.getCk());
        String cacheCapt = redisUtil.get(captchaKey);
        if (!StringUtils.equalsIgnoreCase(requestDto.getCaptcha(), cacheCapt)) {
            log.info("captchaKey:{},cacheCapt,{},captcha: {}", captchaKey, requestDto.getCaptcha(),
                    cacheCapt);
                    return ApiResponse.error(ResponseCode.CAPTCHA_ERROR, null);
        }
        redisUtil.delete(captchaKey);
        AdminUser adminUser = adminUserMapper.getUserByUsername(requestDto.getEmailAndphone());
        if(adminUser == null){
            return ApiResponse.error(ResponseCode.USER_NOT_FOUND, null);
        }
        if(!StringUtils.equalsAnyIgnoreCase(adminUser.getPassword(),requestDto.getPassword())){
            return ApiResponse.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR, null);
        }
        LoginResultDTO resultDTO = new LoginResultDTO();
        resultDTO.setUserName(adminUser.getUsername());
        return ApiResponse.success(resultDTO);
    }

}
