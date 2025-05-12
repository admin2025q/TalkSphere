package com.tt.admin.service.checkservice;

import com.tt.admin.dto.LoginRequestDTO;
import com.tt.admin.dto.LoginResultDTO;
import com.tt.vo.ApiResponse;

public interface LoginService {

    /**
     * 判断请求是否重复 (只基于用户ID和路径)
     *
     * @param anonyUid anonyUid
     * @param captcha  验证码
     * @return true 如果是重复请求，false 否则
     */
    public String setAnonyUser(String anonyUid, String captcha);

    /**
     * 登录
     *
     * @param requestDto 登录请求参数
     * @return 登录结果
     */
    public ApiResponse<LoginResultDTO> login(LoginRequestDTO requestDto);

}
