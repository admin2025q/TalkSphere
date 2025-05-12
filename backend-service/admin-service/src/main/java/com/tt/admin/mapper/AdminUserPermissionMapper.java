package com.tt.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tt.admin.entity.AdminUserPermission;

/**
 * <p>
 * 管理员与权限关联表 Mapper 接口
 * </p>
 *
 * @author tt
 * @since 2025-05-12
 */
public interface AdminUserPermissionMapper extends BaseMapper<AdminUserPermission> {

    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<AdminUserPermission> selectListByUid(Long userId);
    
}
