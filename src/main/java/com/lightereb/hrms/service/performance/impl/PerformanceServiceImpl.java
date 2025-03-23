package com.lightereb.hrms.service.performance.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.performance.PerformanceMapper;
import com.lightereb.hrms.model.entity.performance.Performance;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.performance.PerformanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * 绩效Service实现类
 */
@Slf4j
@Service
public class PerformanceServiceImpl extends BaseServiceImpl<PerformanceMapper, Performance> implements PerformanceService {

	@Override
	public Performance getMonthPerformance(Long empId, Integer year, Integer month) {
		return baseMapper.selectMonthPerformance(empId, year, month);
	}

	@Override
	public List<Performance> getDeptMonthPerformance(Long deptId, Integer year, Integer month) {
		return baseMapper.selectDeptMonthPerformance(deptId, year, month);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitSelfEvaluation(Long performanceId, String selfEvaluation) {
		Performance performance = getById(performanceId);
		if (performance == null) {
			throw new BusinessException("绩效记录不存在");
		}

		performance.setSelfEvaluation(selfEvaluation);
		updateById(performance);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitEvaluation(Long performanceId, Performance evaluation) {
		Performance performance = getById(performanceId);
		if (performance == null) {
			throw new BusinessException("绩效记录不存在");
		}

		// 更新评分
		performance.setWorkPerformance(evaluation.getWorkPerformance());
		performance.setAbilityScore(evaluation.getAbilityScore());
		performance.setAttitudeScore(evaluation.getAttitudeScore());
		performance.setEvaluationContent(evaluation.getEvaluationContent());
		performance.setImprovementPlan(evaluation.getImprovementPlan());

		// 计算总评分并确定等级
		calculateTotalScore(performance);
		performance.setAssessmentLevel(determineAssessmentLevel(performance.getTotalScore()));

		// 设置考核时间
		performance.setAssessmentTime(LocalDate.now());

		updateById(performance);
	}

	@Override
	public void calculateTotalScore(Performance performance) {
		// 计算总分（假设权重：工作业绩40%，能力30%，态度30%）
		BigDecimal totalScore = performance.getWorkPerformance().multiply(BigDecimal.valueOf(0.4))
				.add(performance.getAbilityScore().multiply(BigDecimal.valueOf(0.3)))
				.add(performance.getAttitudeScore().multiply(BigDecimal.valueOf(0.3)))
				.setScale(1, RoundingMode.HALF_UP);

		performance.setTotalScore(totalScore);
	}

	@Override
	public String determineAssessmentLevel(BigDecimal totalScore) {
		double score = totalScore.doubleValue();
		if (score >= 9.0) {
			return "A";
		} else if (score >= 8.0) {
			return "B";
		} else if (score >= 7.0) {
			return "C";
		} else {
			return "D";
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void generateMonthlyPerformance(Integer year, Integer month) {
		// TODO: 获取所有在职员工
		// TODO: 为每个员工创建绩效记录
		// TODO: 设置基本信息（年份、月份等）
	}
}