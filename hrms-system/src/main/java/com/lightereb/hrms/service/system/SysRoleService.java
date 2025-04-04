package com.lightereb.hrms.service.system;

import com.lightereb.hrms.common.service.BaseService;
import com.lightereb.hrms.model.entity.system.SysRole;

import java.util.List;

/**
 * 角色Service接口
 */
public interface SysRoleService extends BaseService<SysRole> {

	/**
	 * 获取用户角色列表
	 */
	List<SysRole> getRolesByUserId(Long userId);

	/**
	 * 分配角色权限
	 */
	void assignRolePermissions(Long roleId, List<Long> permissionIds);

	/**
	 * 获取角色的权限ID列表
	 */
	List<Long> getRolePermissionIds(Long roleId);

	/**
	 * 获取用户角色编码列表
	 */
	List<String> getRoleCodesByUserId(Long userId);

	/**
	 * 获取用户权限编码列表
	 */
	List<String> getPermissionCodesByUserId(Long userId);
}