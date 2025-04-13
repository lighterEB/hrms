package com.lightereb.hrms.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.mapper.system.SysUserMapper;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.system.SysPermissionService;
import com.lightereb.hrms.service.system.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义UserDetailsService实现
 * 用于从数据库加载用户信息
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;
    private final SysRoleService roleService;
    private final SysPermissionService permissionService;

    /**
     * 根据用户名加载用户信息
     * @param username 用户名
     * @return 用户详情
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查询用户
        SysUser user = userMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
                        .eq(SysUser::getStatus, 1) // 只查询状态正常的用户
        );

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在或已禁用");
        }

        // 查询用户角色
        List<String> roleCodes = roleService.getRoleCodesByUserId(user.getId());
        
        // 查询用户权限
        Set<String> permissions = permissionService.getUserPermissions(user.getId());
        
        // 将角色和权限转换为Spring Security的权限格式
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // 角色必须添加ROLE_前缀
        roleCodes.forEach(roleCode -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
        });
        
        // 添加权限
        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}