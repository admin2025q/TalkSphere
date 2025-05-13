package com.tt.admin.repository.adminuser.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tt.admin.entity.dao.AdminMenu;
import com.tt.admin.mapper.AdminMenuMapper;
import com.tt.admin.mapper.AdminUserPermissionMapper;
import com.tt.admin.repository.adminuser.AdminMenuRepository;
import com.tt.admin.repository.repositoryHandler.LocalCacheHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminMenuRepositoryImpl extends LocalCacheHandler<AdminMenu> implements AdminMenuRepository {

    private final AdminMenuMapper adminMenuMapper;
    private final AdminUserPermissionMapper adminUserPermissionMapper;

    private static final String MENU_CACHE_KEY_PREFIX = "menu:";
    private static final String MENU_CACHE_LIST_KEY_PREFIX = "menu:all";

    private final Cache<String, AdminMenu> cache = Caffeine.newBuilder()
            // 缓存 1 小时
            .expireAfterWrite(1, TimeUnit.HOURS)
            // 最大缓存 100 个角色
            .maximumSize(1000)
            .build();

    private final Cache<String, List<AdminMenu>> cacheList = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .build();

    @Override
    public AdminMenu getMenuById(Long id) {
        AdminMenu menuCache = loadCache(MENU_CACHE_KEY_PREFIX + id);
        if (menuCache != null) {
            return menuCache;
        }
        menuCache = adminMenuMapper.selectById(id);
        return menuCache;
    }

    @Override
    public List<AdminMenu> getAllMenus() {
        List<AdminMenu> adminList = cacheList.getIfPresent(MENU_CACHE_LIST_KEY_PREFIX);
        if (!CollectionUtils.isEmpty(adminList)) {
            return adminList;
        }
        adminList = adminMenuMapper.getAllMenus();
        if (CollectionUtils.isEmpty(adminList)) {
            throw new RuntimeException("菜单列表为空");
        }
        adminList.forEach(item -> cache.put(MENU_CACHE_KEY_PREFIX + item.getId(), item));
        cacheList.put(MENU_CACHE_LIST_KEY_PREFIX, adminList);
        return adminList;
    }

    @Override
    protected Cache<String, AdminMenu> getCache() {
        return cache;
    }

    @Override
    public void clearMenuCache() {
        super.clearMenuCache();
        cacheList.invalidateAll();
    }

    @Override
    public void refresh() {
        this.clearMenuCache();
        this.getAllMenus();
        log.info("菜单缓存刷新成功");
    }
    

}
