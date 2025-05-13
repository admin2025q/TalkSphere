package com.tt.admin.service.adminuser;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tt.admin.entity.dao.AdminPermission;
import com.tt.admin.entity.dao.MenuPermission;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
public interface AdminPermissionService extends IService<AdminPermission> {

        List<MenuPermission> selectAllPermission();
}
