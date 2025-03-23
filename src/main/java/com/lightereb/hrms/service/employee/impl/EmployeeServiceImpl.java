package com.lightereb.hrms.service.employee.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.employee.EmployeeMapper;
import com.lightereb.hrms.model.entity.employee.Employee;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 员工Service实现类
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

	@Override
	public Employee getByEmpNo(String empNo) {
		return baseMapper.selectByEmpNo(empNo);
	}

	@Override
	public Employee getByUserId(Long userId) {
		return baseMapper.selectByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateEmploymentStatus(Long empId, Integer status) {
		Employee employee = getById(empId);
		if (employee == null) {
			throw new BusinessException("员工不存在");
		}
		employee.setEmploymentStatus(status);
		updateById(employee);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void onboard(Employee employee) {
		// 设置入职日期
		employee.setEntryDate(LocalDate.now());
		// 设置在职状态
		employee.setEmploymentStatus(2); // 试用期
		save(employee);
		// TODO: 处理其他入职相关业务
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resign(Long empId, String reason) {
		Employee employee = getById(empId);
		if (employee == null) {
			throw new BusinessException("员工不存在");
		}
		// 设置离职日期
		employee.setLeaveDate(LocalDate.now());
		// 设置离职状态
		employee.setEmploymentStatus(0); // 离职
		updateById(employee);
		// TODO: 处理其他离职相关业务
	}
}