package com.tt.admin.entity.vo;

import java.util.List;

import org.checkerframework.checker.units.qual.N;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * <p>
 * 菜单表       
 * * </p>
 * * @author tt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenuVo {

  
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
     * 子菜单列表
     */
    private List<AdminMenuVo> children;


}
