package com.tt.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tt.admin.entity.dao.AdminMenu;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 * @author tt
 * @since 2025-05-10
 */
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

    List<AdminMenu> getAllMenus();

}
