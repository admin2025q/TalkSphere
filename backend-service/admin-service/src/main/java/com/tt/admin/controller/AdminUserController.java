package com.tt.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tt.vo.ApiResponse;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @RequestMapping("/pageListUser")
    public ApiResponse<String> pageListUsers(){
    
        return ApiResponse.success(null);
    }
}
