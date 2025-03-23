package com.lightereb.hrms.service.salary.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.salary.SalaryMapper;
import com.lightereb.hrms.model.entity.salary.Salary;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.salary.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 薪资Service实现类
 */
@Slf4j
@Service
public class SalaryServiceImpl extends BaseServiceImpl<SalaryMapper, Salary> implements SalaryService {

	@Override
	public Salary getMonthSalary(Long empId, Integer year, Integer month) {
		return baseMapper.selectMonthSalary(empId, year, month);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void generateMonthlySalary(Integer year, Integer month) {
		// TODO: 获取所有在职员工
		// TODO: 遍历员工生成薪资记录
		// TODO: 计算各项薪资项目
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void paySalary(Long salaryId) {
		Salary salary = getById(salaryId);
		if (salary == null) {
			throw new BusinessException("薪资记录不存在");
		}

		if (salary.getPaymentStatus().equals(1)) {
			throw new BusinessException("该薪资已发放");
		}

		salary.setPaymentStatus(1);
		salary.setPaymentTime(LocalDateTime.now());
		updateById(salary);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchPaySalary(List<Long> salaryIds) {
		salaryIds.forEach(this::paySalary);
	}

	@Override
	public List<Salary> getUnpaidSalaries(Integer year, Integer month) {
		return baseMapper.selectUnpaidSalaries(year, month);
	}

	@Override
	public void calculateSalary(Salary salary) {
		// 计算实发工资 = 基本工资 + 奖金 + 津贴 + 加班费 - 社保 - 公积金 - 个税 - 其他扣款
		BigDecimal actualSalary = salary.getBasicSalary()
				.add(salary.getBonus())
				.add(salary.getAllowance())
				.add(salary.getOvertimePay())
				.subtract(salary.getSocialSecurity())
				.subtract(salary.getHousingFund())
				.subtract(salary.getTax())
				.subtract(salary.getOtherDeduction());

		salary.setActualSalary(actualSalary);
	}
}