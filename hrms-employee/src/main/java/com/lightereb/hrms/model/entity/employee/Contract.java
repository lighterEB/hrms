package com.lightereb.hrms.model.entity.employee;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 合同管理表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_contract")
public class Contract extends BaseEntity {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 合同编号
	 */
	private String contractNo;

	/**
	 * 合同类型
	 */
	private String contractType;

	/**
	 * 开始日期
	 */
	private LocalDate startDate;

	/**
	 * 结束日期
	 */
	private LocalDate endDate;

	/**
	 * 试用期(月)
	 */
	private Integer probationMonths;

	/**
	 * 工作描述
	 */
	private String jobDescription;

	/**
	 * 薪资金额
	 */
	private BigDecimal salaryAmount;

	/**
	 * 合同状态 0-终止 1-生效 2-到期
	 */
	private Integer contractStatus;

	/**
	 * 签订日期
	 */
	private LocalDate signDate;

	/**
	 * 终止日期
	 */
	private LocalDate terminationDate;

	/**
	 * 终止原因
	 */
	private String terminationReason;

	/**
	 * 附件URL
	 */
	private String attachmentUrl;

	/**
	 * 备注
	 */
	private String remark;
}