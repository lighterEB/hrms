package com.lightereb.hrms.service.system;

import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.service.BaseService;

public interface SysRoleService extends BaseService<SysRole> {
	/**
	 * 根据角色编码查询角色
	 */
	SysRole getByRoleCode(String roleCode);
}