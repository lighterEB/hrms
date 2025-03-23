package com.lightereb.hrms.service.employee;

import com.lightereb.hrms.model.entity.employee.Employee;
import com.lightereb.hrms.service.BaseService;

/**
 * 员工Service接口
 */
public interface EmployeeService extends BaseService<Employee> {

	/**
	 * 根据员工编号查询员工
	 */
	Employee getByEmpNo(String empNo);

	/**
	 * 根据用户ID查询员工
	 */
	Employee getByUserId(Long userId);

	/**
	 * 更新员工状态
	 */
	void updateEmploymentStatus(Long empId, Integer status);

	/**
	 * 员工入职
	 */
	void onboard(Employee employee);

	/**
	 * 员工离职
	 */
	void resign(Long empId, String reason);
}