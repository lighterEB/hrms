package com.lightereb.hrms.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 权限菜单表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity
{

	/**
	 * 父ID
	 */
	private Long parentId;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * 路由路径
	 */
	private String path;

	/**
	 * 组件路径
	 */
	private String component;

	/**
	 * 权限标识
	 */
	private String perms;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 类型 0-目录 1-菜单 2-按钮
	 */
	private Integer type;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态 0-禁用 1-正常
	 */
	private Integer status;
}