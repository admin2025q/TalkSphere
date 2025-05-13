package com.tt.admin.repository.adminuser;

import java.util.List;

import com.tt.admin.entity.dao.AdminPermission;

public interface AdminPermissionReposity {

    public AdminPermission getAdminPermissionById(Long id);

    public List<AdminPermission> selecListAll();

       /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<AdminPermission> selectListByUid(Long userId);
    
}
