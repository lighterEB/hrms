package com.lightereb.hrms.service.performance;

import com.lightereb.hrms.model.entity.performance.Performance;
import com.lightereb.hrms.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效Service接口
 */
public interface PerformanceService extends BaseService<Performance> {

	/**
	 * 获取员工月度绩效
	 */
	Performance getMonthPerformance(Long empId, Integer year, Integer month);

	/**
	 * 获取部门月度绩效
	 */
	List<Performance> getDeptMonthPerformance(Long deptId, Integer year, Integer month);

	/**
	 * 提交自我评价
	 */
	void submitSelfEvaluation(Long performanceId, String selfEvaluation);

	/**
	 * 提交评估内容
	 */
	void submitEvaluation(Long performanceId, Performance performance);

	/**
	 * 计算总评分
	 */
	void calculateTotalScore(Performance performance);

	/**
	 * 确定考核等级
	 */
	String determineAssessmentLevel(BigDecimal totalScore);

	/**
	 * 生成月度绩效记录
	 */
	void generateMonthlyPerformance(Integer year, Integer month);
}