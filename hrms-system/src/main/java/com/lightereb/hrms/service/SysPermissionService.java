package com.lightereb.hrms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lightereb.hrms.model.entity.system.SysPermission;
import com.lightereb.hrms.model.vo.system.MenuVO;

import java.util.List;
import java.util.Set;

/**
 * 权限服务接口
 */
public interface SysPermissionService extends IService<SysPermission> {
    
    /**
     * 获取用户的菜单树
     * @param userId 用户ID
     * @return 菜单树
     */
    List<MenuVO> getUserMenuTree(Long userId);
    
    /**
     * 获取用户的权限集合
     * @param userId 用户ID
     * @return 权限集合
     */
    Set<String> getUserPermissions(Long userId);
    
    /**
     * 构建权限树
     * @param permissions 权限列表
     * @return 权限树
     */
    List<SysPermission> buildPermissionTree(List<SysPermission> permissions);
} 