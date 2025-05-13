package com.tt.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tt.admin.entity.dao.AdminMenu;
import com.tt.admin.entity.dao.AdminPermission;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
public interface AdminPermissionMapper extends BaseMapper<AdminPermission> {

    List<AdminPermission> selecList();
}
