package com.lightereb.hrms.service.system.impl;

import com.lightereb.hrms.common.service.PermissionService;
import com.lightereb.hrms.service.system.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * PermissionService接口实现类
 * 桥接基础权限服务和系统权限服务
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final SysPermissionService sysPermissionService;
    
    @Override
    public Set<String> getUserPermissions(Long userId) {
        return sysPermissionService.getUserPermissions(userId);
    }
    
    @Override
    public boolean hasPermission(Long userId, String permission) {
        Set<String> permissions = getUserPermissions(userId);
        return permissions != null && permissions.contains(permission);
    }
} 