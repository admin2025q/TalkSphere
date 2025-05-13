package com.tt.admin.service.adminuser.impl;

import com.tt.admin.entity.dao.AdminMenu;
import com.tt.admin.entity.dao.AdminMenuPermission;
import com.tt.admin.entity.dao.AdminPermission;
import com.tt.admin.entity.dao.AdminUserPermission;
import com.tt.admin.entity.vo.AdminMenuVo;
import com.tt.admin.mapper.AdminMenuMapper;
import com.tt.admin.mapper.AdminMenuPermissionMapper;
import com.tt.admin.repository.adminuser.AdminMenuRepository;
import com.tt.admin.repository.adminuser.AdminPermissionReposity;
import com.tt.admin.service.adminuser.AdminMenuService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.tools.framedump;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {

    private final AdminPermissionReposity adminPermissionReposity;

    private final AdminMenuRepository adminMenuRepository;

    private final AdminMenuPermissionMapper adminMenuPermissionMapper;

    @Override
    public List<AdminMenuVo> selectListByUid(Long userId) {
        // 查询用户权限
        List<AdminPermission> userPermission = adminPermissionReposity.selectListByUid(userId);
        // 根据用户权限查询他的菜单
        List<AdminMenuPermission> raltionList = adminMenuPermissionMapper
                .selectMenuIdByPermissionId(userPermission.stream().map(AdminPermission::getId).toList());
        ;
        // 根据权限获取菜单列表
        List<AdminMenu> allMenu = adminMenuRepository.getAllMenus();
        // 过滤出菜单列表
        List<AdminMenu> userMenus = allMenu.stream()
                .filter(menu -> raltionList.stream().anyMatch(item -> item.getMenuId().equals(menu.getId()))).toList();

        List<AdminMenuVo> resulList = userMenus.stream().map(this::convert).toList();

        for (AdminMenuVo vo : resulList) {
            vo.setChildren(findSubMenus(resulList, vo.getId()));
        }

        return resulList.stream().filter(vo -> vo.getParentId() == 0L).toList();
    }

    // 查找所有子菜单
    private List<AdminMenuVo> findSubMenus(List<AdminMenuVo> menus, Long parentId) {
        List<AdminMenuVo> subMenus = new ArrayList<>();
        for (AdminMenuVo menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                subMenus.add(menu);
                // 递归查找子菜单的子菜单
                List<AdminMenuVo> children = findSubMenus(menus, menu.getId());
                menu.setChildren(children);
            }
        }
        return subMenus;
    }

    private AdminMenuVo convert(AdminMenu adminMenu) {
        AdminMenuVo adminMenuVo = new AdminMenuVo();
        adminMenuVo.setId(adminMenu.getId());
        adminMenuVo.setName(adminMenu.getName());
        adminMenuVo.setPath(adminMenu.getPath());
        adminMenuVo.setParentId(adminMenu.getParentId());
        adminMenuVo.setIcon(adminMenu.getIcon());
        adminMenuVo.setSort(adminMenu.getSort());
        return adminMenuVo;
    }

}
