package com.tt;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tt.admin.entity.AdminUser;
import com.tt.admin.mapper.AdminUserMapper;
import com.tt.admin.service.adminuser.AdminUserService;
import com.tt.admin.util.JsonUtil;
import com.tt.admin.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class AdminServiceApplicationTests {

    @Autowired
    private AdminUserService adminUserService;
   
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFindAllUsers() {

        // 查询所有用户
        List<AdminUser> users = adminUserService.list();
        log.info("所有用户: {}", JsonUtil.toJson(users));
    }

    @Test
    public void testFindUserById() {
        // 查询用户
        AdminUser user = adminUserService.getById(1L);
        log.info("用户: {}", JsonUtil.toJson(user));

        AdminUser user1 = adminUserService.getById(1L);
        log.info("用户1: {}", JsonUtil.toJson(user1));
    }

    @Test
    @Transactional
    public void testSelectById() {
        // 查询 ID 为 1 的用户
        AdminUser user = adminUserMapper.selectById(1L);
        AdminUser user1 = adminUserMapper.selectById(1L);
        redisUtil.set("a", "aaaa");
        redisUtil.set("a", JsonUtil.toJson(user1));
        log.info("用户信息: {}", user);
    }

    @Test
    public void testSelectPage() {
        IPage<AdminUser> page = new Page<AdminUser>(1, 1);
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        IPage<AdminUser> selectPage = adminUserMapper.selectPage(page,queryWrapper);;
        log.info("用户信息: {}", JsonUtil.toJson(selectPage.getRecords()));
    }
}