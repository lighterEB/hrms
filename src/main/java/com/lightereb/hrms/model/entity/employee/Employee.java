package com.lightereb.hrms.model.entity.employee;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_employee")
public class Employee extends BaseEntity {

	/**
	 * 员工编号
	 */
	private String empNo;

	/**
	 * 员工姓名
	 */
	private String empName;

	/**
	 * 性别 0-女 1-男
	 */
	private Integer gender;

	/**
	 * 出生日期
	 */
	private LocalDate birthDate;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 学历
	 */
	private String education;

	/**
	 * 部门ID
	 */
	private Long deptId;

	/**
	 * 职位ID
	 */
	private Long positionId;

	/**
	 * 入职日期
	 */
	private LocalDate entryDate;

	/**
	 * 离职日期
	 */
	private LocalDate leaveDate;

	/**
	 * 在职状态 0-离职 1-在职 2-试用期
	 */
	private Integer employmentStatus;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 紧急联系人
	 */
	private String emergencyContact;

	/**
	 * 紧急联系人电话
	 */
	private String emergencyPhone;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 关联系统用户ID
	 */
	private Long userId;
}