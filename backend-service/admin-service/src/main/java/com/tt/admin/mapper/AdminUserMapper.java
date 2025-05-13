package com.tt.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tt.admin.entity.dao.AdminUser;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

     /**
     * 根据用户名查询管理员用户
     *
     * @param username 用户名
     * @return 管理员用户实体
     */
    @Select("SELECT * FROM admin_user WHERE username = #{username}")
    AdminUser getUserByUsername(@Param("username") String username);

}
