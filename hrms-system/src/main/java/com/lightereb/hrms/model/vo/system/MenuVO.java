package com.lightereb.hrms.model.vo.system;

import lombok.Data;

import java.util.List;

/**
 * 菜单视图对象
 */
@Data
public class MenuVO {
    /**
     * 菜单ID
     */
    private Long id;
    
    /**
     * 菜单名称
     */
    private String name;
    
    /**
     * 菜单路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 排序
     */
    private Integer orderNum;
    
    /**
     * 父菜单ID
     */
    private Long parentId;
    
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 子菜单列表
     */
    private List<MenuVO> children;
} 