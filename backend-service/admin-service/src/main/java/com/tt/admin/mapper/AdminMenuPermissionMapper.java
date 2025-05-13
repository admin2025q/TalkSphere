package com.tt.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tt.admin.entity.dao.AdminMenuPermission;
import com.tt.admin.entity.dao.MenuPermission;

/**
 * <p>
 * 菜单与权限关联表 Mapper 接口
 * </p>
 *
 * @author tt
 * @since 2025-05-12
 */
public interface AdminMenuPermissionMapper extends BaseMapper<AdminMenuPermission> {

    List<AdminMenuPermission> selectMenuIdByPermissionId(@Param("pids") List<Long> pids);

    @Select("""
    SELECT m.path , p.code FROM admin_menu m 
            INNER JOIN admin_menu_permission mp ON m.id = mp.menu_id 
            INNER JOIN admin_permission p ON mp.permission_id = p.id 
    """)
    List<MenuPermission> selectAllPermission();
}
