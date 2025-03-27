package com.lightereb.hrms.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.mapper.system.SysRoleMapper;
import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.system.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Override
	public SysRole getByRoleCode(String roleCode) {
		return getOne(new LambdaQueryWrapper<SysRole>()
				.eq(SysRole::getRoleCode, roleCode));
	}
}