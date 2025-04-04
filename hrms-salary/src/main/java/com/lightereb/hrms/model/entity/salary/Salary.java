package com.lightereb.hrms.model.entity.salary;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 薪资表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_salary")
public class Salary extends BaseEntity {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 年份
	 */
	private Integer year;

	/**
	 * 月份
	 */
	private Integer month;

	/**
	 * 基本工资
	 */
	private BigDecimal basicSalary;

	/**
	 * 奖金
	 */
	private BigDecimal bonus;

	/**
	 * 津贴
	 */
	private BigDecimal allowance;

	/**
	 * 加班费
	 */
	private BigDecimal overtimePay;

	/**
	 * 社保扣款
	 */
	private BigDecimal socialSecurity;

	/**
	 * 公积金扣款
	 */
	private BigDecimal housingFund;

	/**
	 * 个税扣款
	 */
	private BigDecimal tax;

	/**
	 * 其他扣款
	 */
	private BigDecimal otherDeduction;

	/**
	 * 实发工资
	 */
	private BigDecimal actualSalary;

	/**
	 * 发放状态 0-未发放 1-已发放
	 */
	private Integer paymentStatus;

	/**
	 * 发放时间
	 */
	private LocalDateTime paymentTime;

	/**
	 * 备注
	 */
	private String remark;
}