package com.lightereb.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.common.service.impl.BaseServiceImpl;
import com.lightereb.hrms.mapper.system.SysPermissionMapper;
import com.lightereb.hrms.mapper.system.SysRoleMapper;
import com.lightereb.hrms.mapper.system.SysRolePermissionMapper;
import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.model.entity.system.SysRolePermission;
import com.lightereb.hrms.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色Service实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{

	private final SysRoleMapper roleMapper;
	private final SysPermissionMapper permissionMapper;
	private final SysRolePermissionMapper rolePermissionMapper;

	@Override
	public List<SysRole> getRolesByUserId(Long userId)
	{
		return baseMapper.selectRolesByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void assignRolePermissions(Long roleId, List<Long> permissionIds)
	{
		// 先删除原有权限
		rolePermissionMapper.delete(
			new LambdaQueryWrapper<SysRolePermission>()
				.eq(SysRolePermission::getRoleId, roleId)
		);
		
		// 如果权限ID列表为空，则只删除不添加
		if (permissionIds == null || permissionIds.isEmpty()) {
			return;
		}
		
		// 批量添加新权限
		List<SysRolePermission> rolePermissions = permissionIds.stream()
			.map(permissionId -> {
				SysRolePermission rolePermission = new SysRolePermission();
				rolePermission.setRoleId(roleId);
				rolePermission.setPermissionId(permissionId);
				return rolePermission;
			})
			.collect(Collectors.toList());
		
		// 逐个插入权限
		for (SysRolePermission rolePermission : rolePermissions) {
			rolePermissionMapper.insert(rolePermission);
		}
	}

	@Override
	public List<Long> getRolePermissionIds(Long roleId)
	{
		// 查询角色关联的所有权限ID
		List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(
			new LambdaQueryWrapper<SysRolePermission>()
				.eq(SysRolePermission::getRoleId, roleId)
		);
		
		// 如果没有关联权限，返回空列表
		if (rolePermissions == null || rolePermissions.isEmpty()) {
			return new ArrayList<>();
		}
		
		// 提取权限ID
		return rolePermissions.stream()
			.map(SysRolePermission::getPermissionId)
			.collect(Collectors.toList());
	}

	@Override
	public List<String> getRoleCodesByUserId(Long userId) {
		return roleMapper.selectRoleCodesByUserId(userId);
	}

	@Override
	public List<String> getPermissionCodesByUserId(Long userId) {
		return permissionMapper.selectPermissionCodesByUserId(userId);
	}
}