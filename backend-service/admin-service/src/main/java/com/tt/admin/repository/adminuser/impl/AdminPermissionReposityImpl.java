package com.tt.admin.repository.adminuser.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.s;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tt.admin.entity.AdminMenu;
import com.tt.admin.entity.AdminPermission;
import com.tt.admin.entity.AdminUserPermission;
import com.tt.admin.mapper.AdminMenuMapper;
import com.tt.admin.mapper.AdminPermissionMapper;
import com.tt.admin.mapper.AdminUserPermissionMapper;
import com.tt.admin.repository.adminuser.AdminMenuRepository;
import com.tt.admin.repository.adminuser.AdminPermissionReposity;
import com.tt.admin.repository.repositoryHandler.LocalCacheHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class AdminPermissionReposityImpl extends LocalCacheHandler<AdminPermission> implements AdminPermissionReposity {

    private final AdminPermissionMapper adminPermissionMapper;
    private final AdminUserPermissionMapper adminUserPermissionMapper;

    private static final String P_CACHE_KEY_PREFIX = "permiss:";
    private static final String P_CACHE_LIST_KEY_PREFIX = "permiss:all";

    private final Cache<String, AdminPermission> cachep = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS) // 缓存 1 小时
            .maximumSize(1000) // 最大缓存 100 个角色
            .build();

    private final Cache<String, List<AdminPermission>> cacheList = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS) // 缓存 1 小时
            .maximumSize(1000) // 最大缓存 100 个角色
            .build();

    public AdminPermission getAdminPermissionById(Long id) {
        AdminPermission cacheAdmin = loadCache(P_CACHE_KEY_PREFIX + id);
        if (cacheAdmin != null) {
            return cacheAdmin;
        }
        AdminPermission selectById = adminPermissionMapper.selectById(id);
        cachep.put(P_CACHE_KEY_PREFIX + id, selectById);
        return selectById;
    }

    @Override
    public List<AdminPermission> selecListAll() {
        adminPermissionMapper.selecList();

        List<AdminPermission> permissions = cacheList.getIfPresent(P_CACHE_LIST_KEY_PREFIX);
        if (!CollectionUtils.isEmpty(permissions)) {
            return permissions;
        }
        List<AdminPermission> pAdminPermissions = adminPermissionMapper.selecList();
        if (CollectionUtils.isEmpty(pAdminPermissions)) {
            throw new RuntimeException("权限列表为空");
        }
        pAdminPermissions.stream().forEach(item -> {
            cachep.put(P_CACHE_KEY_PREFIX + item.getId(), item);
        });
        cacheList.put(P_CACHE_LIST_KEY_PREFIX, pAdminPermissions);
        return pAdminPermissions;
    }

    @Override
    public List<String> selectListByUid(Long userId) {
        List<AdminUserPermission> raltionList = adminUserPermissionMapper.selectListByUid(userId);
        List<AdminPermission> selecListAll = selecListAll();
        List<String> permissionList = selecListAll.stream()
                .filter(item -> raltionList.stream().anyMatch(r -> r.getPermissionId().equals(item.getId())))
                .map(AdminPermission::getCode)
                .toList();
        return permissionList;
    }

    @Override
    public void clearMenuCache() {
        super.clearMenuCache();
        cacheList.invalidateAll();
    }

    @Override
    public void refresh() {
       this.clearMenuCache();
        this.selecListAll();
        log.info("权限缓存刷新成功");
    }

    @Override
    protected Cache<String, AdminPermission> getCache() {
        return cachep;
    }

}
