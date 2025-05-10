package com.tt.admin.service.impl;

import com.tt.admin.entity.AdminUser;
import com.tt.admin.mapper.AdminUserMapper;
import com.tt.admin.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

}
