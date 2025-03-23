package com.lightereb.hrms.model.entity.organization;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_department")
public class Department extends BaseEntity {

	/**
	 * 父部门ID
	 */
	private Long parentId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 部门编码
	 */
	private String deptCode;

	/**
	 * 负责人
	 */
	private String leader;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 状态 0-禁用 1-正常
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;
}