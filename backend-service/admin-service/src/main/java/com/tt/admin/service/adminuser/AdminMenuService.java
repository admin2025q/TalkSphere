package com.tt.admin.service.adminuser;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tt.admin.entity.dao.AdminMenu;
import com.tt.admin.entity.vo.AdminMenuVo;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
public interface AdminMenuService extends IService<AdminMenu> {

    /**
     * 获取所有菜单
     * @return 菜单列表
     * @param userId
     * @return
     */
    List<AdminMenuVo> selectListByUid(Long userId);
}
