package com.lightereb.hrms.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lightereb.hrms.model.entity.system.SysPermission;
import com.lightereb.hrms.model.vo.system.MenuVO;

import java.util.List;
import java.util.Set;

public interface SysPermissionService extends IService<SysPermission>
{
	/**
	 * 获取用户的菜单树
	 * @param userId 用户ID
	 * @return 菜单树
	 */
	List<MenuVO> getUserMenuTree(Long userId);

	/**
	 * 获取用户的权限列表
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	Set<String> getUserPermissions(Long userId);
}
