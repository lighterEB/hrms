package com.lightereb.hrms.service.system.impl;

import com.lightereb.hrms.mapper.system.SysPermissionMapper;
import com.lightereb.hrms.mapper.system.SysRoleMapper;
import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.system.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{

	private final SysRoleMapper roleMapper;
	private final SysPermissionMapper permissionMapper;

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
		// TODO: 实现删除和添加权限的逻辑
	}

	@Override
	public List<Long> getRolePermissionIds(Long roleId)
	{
		// TODO: 实现获取角色权限ID列表的逻辑
		return null;
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