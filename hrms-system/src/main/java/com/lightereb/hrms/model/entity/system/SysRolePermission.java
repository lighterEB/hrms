package com.lightereb.hrms.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色权限关联表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_permission")
public class SysRolePermission extends BaseEntity
{

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 权限ID
	 */
	private Long permissionId;
}