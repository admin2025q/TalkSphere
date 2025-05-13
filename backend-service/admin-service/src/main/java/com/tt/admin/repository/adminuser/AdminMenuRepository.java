package com.tt.admin.repository.adminuser;

import java.util.List;

import com.tt.admin.entity.dao.AdminMenu;

public interface AdminMenuRepository {

    public AdminMenu getMenuById(Long id);

    public List<AdminMenu> getAllMenus();
}