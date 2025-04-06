package com.lightereb.hrms.model.entity.performance;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 绩效评估表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_performance")
public class Performance extends BaseEntity {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 考核年份
	 */
	private Integer assessmentYear;

	/**
	 * 考核月份
	 */
	private Integer assessmentMonth;

	/**
	 * 工作业绩(0-10)
	 */
	private BigDecimal workPerformance;

	/**
	 * 能力评分(0-10)
	 */
	private BigDecimal abilityScore;

	/**
	 * 态度评分(0-10)
	 */
	private BigDecimal attitudeScore;

	/**
	 * 总评分(0-10)
	 */
	private BigDecimal totalScore;

	/**
	 * 考核等级(A、B、C、D)
	 */
	private String assessmentLevel;

	/**
	 * 评估人ID
	 */
	private Long evaluatorId;

	/**
	 * 自我评价
	 */
	private String selfEvaluation;

	/**
	 * 评估内容
	 */
	private String evaluationContent;

	/**
	 * 改进计划
	 */
	private String improvementPlan;

	/**
	 * 考核时间
	 */
	private LocalDate assessmentTime;

	/**
	 * 备注
	 */
	private String remark;
}