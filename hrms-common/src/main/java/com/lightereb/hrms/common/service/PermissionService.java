package com.lightereb.hrms.common.service;

import java.util.Set;

/**
 * 基础权限服务接口
 * 提供基本的权限检查功能
 */
public interface PermissionService {
    
    /**
     * 获取用户的权限集合
     * @param userId 用户ID
     * @return 权限集合
     */
    Set<String> getUserPermissions(Long userId);
    
    /**
     * 检查用户是否拥有指定权限
     * @param userId 用户ID
     * @param permission 权限标识
     * @return 是否拥有权限
     */
    boolean hasPermission(Long userId, String permission);
} 