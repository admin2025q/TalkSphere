SELECT '开始处理数据...';
-- 初始化脚本
-- 1. 创建数据库
-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS talksphere CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换到指定的数据库
USE talksphere;

-- 设置默认使用的字符集和排序规则（推荐使用 utf8mb4 和 utf8mb4_unicode_ci）
ALTER DATABASE talksphere CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE admin_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    external_id VARCHAR(50) NOT NULL UNIQUE COMMENT '外部显示ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态（1：启用，0：禁用）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='管理员表';


CREATE TABLE admin_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '权限标识（如：user:add）',
    description VARCHAR(200) COMMENT '权限描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='权限表';

CREATE TABLE admin_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID（0表示顶级菜单）',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path VARCHAR(100) COMMENT '菜单路径（前端路由）',
    icon VARCHAR(50) COMMENT '菜单图标',
    sort INT DEFAULT 0 COMMENT '排序值（越小越靠前）',
    visible TINYINT DEFAULT 1 COMMENT '是否可见（1：可见，0：隐藏）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='菜单表';


CREATE TABLE admin_user_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '管理员ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY (user_id, permission_id)
) COMMENT='管理员与权限关联表';


CREATE TABLE admin_menu_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY (menu_id, permission_id)
) COMMENT='菜单与权限关联表';

START TRANSACTION;
INSERT INTO admin_user (external_id,username, password, nickname, email, phone, status) VALUES
('uuid','admin', '123456', '超级管理员', 'admin@example.com', '1234567890', 1);

INSERT INTO admin_permission (name, code, description) VALUES
('用户管理', 'user:manage', '管理用户的权限'),
('菜单管理', 'menu:manage', '管理菜单的权限');

INSERT INTO admin_menu (parent_id, name, path, icon, sort, visible) VALUES
(0, '系统管理', '/system', 'setting', 1, 1),
(1, '用户管理', '/system/user', 'user', 2, 1),
(1, '菜单管理', '/system/menu', 'menu', 3, 1);


COMMIT;
SELECT '数据处理完成。';
