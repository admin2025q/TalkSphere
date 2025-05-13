package com.tt.admin.service.checkservice;

import com.tt.admin.entity.dto.LoginRequestDTO;
import com.tt.admin.entity.dto.LoginResultDTO;
import com.tt.vo.ApiResponse;

public interface LoginService {

    /**
     * @param captcha 验证码
     * @return true 如果是重复请求，false 否则
     */
    String setAnonyUser(String captcha);

    /**
     * 登录
     *
     * @param requestDto 登录请求参数
     * @return 登录结果
     */
    ApiResponse<LoginResultDTO> login(LoginRequestDTO requestDto);

}
