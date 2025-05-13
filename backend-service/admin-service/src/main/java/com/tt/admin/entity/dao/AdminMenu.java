package com.tt.admin.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author tt
 * @since 2025-05-10
 */
@Getter
@Setter
@TableName("admin_menu")
public class AdminMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID（0表示顶级菜单）
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单路径（前端路由）
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序值（越小越靠前）
     */
    private Integer sort;

    /**
     * 是否可见（1：可见，0：隐藏）
     */
    private Byte visible;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
