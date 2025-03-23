package com.lightereb.hrms.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.system.SysRoleMapper;
import com.lightereb.hrms.mapper.system.SysUserMapper;
import com.lightereb.hrms.mapper.system.SysUserRoleMapper;
import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.model.entity.system.SysUserRole;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.system.SysRoleService;
import com.lightereb.hrms.service.system.SysUserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 系统用户Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysRoleService roleService;
    private final SysUserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLastLoginTime(Long userId) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String newPassword) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(status);
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignDefaultRole(Long userId) {
        // 获取默认角色（假设角色编码为 ROLE_USER）
        SysRole defaultRole = roleService.getOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleCode, "ROLE_USER"));

        if (defaultRole != null) {
            SysUserRole userRole = new SysUserRole()
                    .setUserId(userId)
                    .setRoleId(defaultRole.getId());
            userRoleMapper.insert(userRole);
        }
    }
}