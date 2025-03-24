package com.lightereb.hrms.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends BaseEntity
{

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色编码
	 */
	private String roleCode;

	/**
	 * 角色描述
	 */
	private String description;
}