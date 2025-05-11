package com.tt.admin.service.checkservice;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.tt.admin.util.IPUtils;
import com.tt.admin.util.RedisUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final RedisUtil redisUtil;
    
}
