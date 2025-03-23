package com.lightereb.hrms.model.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {

	private Long id;
	/**
	 * 父ID
	 */
	private Long parentId;

	/**
	 * 权限名称
	 */
	private String name;
	private String path;
	private String component;
	private String icon;
	/**
	 * 类型 0-目录 1-菜单 2-按钮
	 */
	private Integer type;
	private Integer sort;
	private List<MenuVO> children;
}
