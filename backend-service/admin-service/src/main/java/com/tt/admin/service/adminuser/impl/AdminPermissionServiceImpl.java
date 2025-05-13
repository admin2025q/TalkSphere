package com.tt.admin.service.adminuser.impl;

import com.tt.admin.entity.dao.AdminPermission;
import com.tt.admin.entity.dao.MenuPermission;
import com.tt.admin.mapper.AdminMenuPermissionMapper;
import com.tt.admin.mapper.AdminPermissionMapper;
import com.tt.admin.service.adminuser.AdminPermissionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission> implements AdminPermissionService {

    private final AdminMenuPermissionMapper adminMenuPermissionMapper;

    @Override
    public List<MenuPermission> selectAllPermission() {
        return adminMenuPermissionMapper.selectAllPermission();
    }

}
