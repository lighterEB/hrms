package com.lightereb.hrms.model.entity.organization;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_position")
public class Position extends BaseEntity {

	/**
	 * 职位名称
	 */
	private String positionName;

	/**
	 * 职位编码
	 */
	private String positionCode;

	/**
	 * 所属部门ID
	 */
	private Long deptId;

	/**
	 * 状态 0-禁用 1-正常
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;
}