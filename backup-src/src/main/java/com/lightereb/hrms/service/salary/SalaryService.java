package com.lightereb.hrms.service.salary;

import com.lightereb.hrms.model.entity.salary.Salary;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 薪资Service接口
 */
public interface SalaryService extends BaseService<Salary> {

	/**
	 * 获取员工某月薪资
	 */
	Salary getMonthSalary(Long empId, Integer year, Integer month);

	/**
	 * 生成月度薪资
	 */
	void generateMonthlySalary(Integer year, Integer month);

	/**
	 * 发放薪资
	 */
	void paySalary(Long salaryId);

	/**
	 * 批量发放薪资
	 */
	void batchPaySalary(List<Long> salaryIds);

	/**
	 * 获取未发放的薪资记录
	 */
	List<Salary> getUnpaidSalaries(Integer year, Integer month);

	/**
	 * 计算薪资
	 */
	void calculateSalary(Salary salary);
}