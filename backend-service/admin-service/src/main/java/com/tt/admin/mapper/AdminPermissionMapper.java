package com.tt.admin.mapper;

import com.tt.admin.entity.AdminMenu;
import com.tt.admin.entity.AdminPermission;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
